/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;

import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.json.TimesheetJson;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
@Entity
@Table(name="tsheets_timesheet")
public class Timesheet {
	private static final Logger logger = LogManager.getLogger(Timesheet.class);
		
	@Id
	@Column(name="id")
	private long id;

	@Column(name="locked")
	private int locked;
	
	@Column(name="deleted")
	private Boolean deleted = false;
	
	@Lob
	@Column(name="notes")
	private String notes;
		
	/*
	 * NOTE it appears that TSheets isn't recording and/or reporting on this field.
	 */
	@Column(name="created")
	private Date createdDate;
	@Column(name="last_modified")
	private Date lastModifiedDate;
	@Column(name="type")
	private String type;
	@Column(name="on_the_clock")
	private boolean onTheClock;
	@Column(name="start")
	private String start;
	@Column(name="[end]")
	private String end;
	@Column(name="date")
	private Date date;
	@Column(name="duration")
	private long duration;
	@Column(name="location")
	private String location;

	/*
	 * User at the time of the timesheet entry
	 */
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	/*
	 * User's group at the time of the timesheet entry
	 */
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	/*
	 * User's role at the time of the timesheet entry.
	 * Custom field.
	 */
	@Column(name="role")
	private String role;
	
	/*
	 * User's support type at the time of the timesheet entry.
	 * Custom field
	 */
	@Column(name="support_type")
	private String supportType;
	
	@ManyToOne
	@JoinColumn(name="job_code_id")
	private JobCode jobCode;
//
//	@ManyToMany(cascade=CascadeType.ALL)  
//    @JoinTable(name="timesheet_customfieldvalue",
//    	joinColumns=@JoinColumn(name="timesheet_id"),
//    	inverseJoinColumns=@JoinColumn(name="custom_field_id")) 
//	private List<CustomField> customFields;
	
	@OneToMany(mappedBy="timesheet")
	private List<CustomFieldValue> customFieldValue;
	
	/*
	 * calculated time fields
	 */
	@Column(name="local_date")
	@Temporal(TemporalType.DATE)
	private Date localDate;
	@Column(name="local_day")
	private String localDay;	
	@Temporal(TemporalType.TIME)
	@Column(name="local_start_time")
	private Date localStartTime;
	@Temporal(TemporalType.TIME)	
	@Column(name="local_end_time")
	private Date localEndTime;
	@Column(name="tz")
	private String timeZone;
	@Column(name="tz_num", precision=3, scale=1)
	private Double timeZoneNum;
	@Column(name="hours")
	private Double hours;
	
	/*
	 * additional calculated fields
	 */
	@Column(name="source")
	private String source;
	@Column(name="s_month")
	private String month;
	@Column(name="all_activities")
	private Integer allActivities;
	@Column(name="real_events")
	private Integer realEvents;
	@Column(name="real_hours")
	private Double realHours;
	@Column(name="resumed")
	private Integer resumed;
	@Column(name="exclude")
	private Boolean exclude;
	@Column(name="err_entry")
	private String errEntry;
	@Column(name="err_entry_general")
	private String errEntryGeneral;
	@Column(name="week_num")
	private Integer weekNum;
	@Column(name="week_day")
	private String weekDay;
	@Column(name="work_day")
	private String workDay;
	@Column(name="is_manager")
	private Boolean manager;	
	
	/*
	 * custom fields (relationship as separate columns)
	 */
	@Column(name="activity")
	private String activity;
	@Column(name="activity1")
	private String activity1;
	@Column(name="activity2")
	private String activity2;
	@Column(name="activity3")
	private String activity3;
	@Column(name="activity4")
	private String activity4;
	@Column(name="activity5")
	private String activity5;
	@Column(name="quantity")
	private String quantity;
	@Column(name="remotely_resolved")
	private String remotelyResolved;
	@Column(name="resume_prior_activity")
	private String resumePriorActivity;
	
	
	// TODO find out what this is for
//	@Column(name="week")
	private String week;
	
	/**
	 * 
	 */
	public Timesheet() {}
	
	public Timesheet(Timesheet t) {
		setCreatedDate(t.getCreatedDate());
		// TODO change this -- it's a list
		for (CustomFieldValue v : t.getCustomFieldValue()) {
			getCustomFieldValue().add(new CustomFieldValue(v));
		}
//		setCustomFieldValue(t.getCustomFieldValue());
		setDate(t.getDate());
		setDuration(t.getDuration());
		setEnd(t.getEnd());
		setHours(t.getHours());
		setId(t.getId());
		setJobCode(t.getJobCode());
		setLastModifiedDate(t.getLastModifiedDate());
		setLocalDate(t.getLocalDate());
		setLocalDay(t.getLocalDay());
		setLocalEndTime(t.getLocalEndTime());
		setLocalStartTime(t.getLocalStartTime());
		setLocation(t.getLocation());
		setLocked(t.getLocked());
		setNotes(t.getNotes());
		setOnTheClock(t.isOnTheClock());
		setStart(t.getStart());
		setTimeZone(t.getTimeZone());
		setType(t.getType());
		setUser(t.getUser());
		
		// calculated fields
		setManager(t.getManager());
		setMonth(t.getMonth());
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Timesheet copy() {
		return new Timesheet(this);
	}
	
	/**
	 * Copy all the properties present on the JSON object to the Timesheet object.
	 * NOTE the deleted properties is not set by this method as it does not exist on the JSON method.
	 * @param json
	 * @return
	 */
	public static Timesheet mapFromJson(TimesheetJson json) {
		Timesheet ts = new Timesheet();
		//  copy the bean properties
		BeanUtils.copyProperties(json, ts, "lastModified", "created", "date");
		// set the special bean properties
		ts.setLastModifiedDate(DateHelper.parseIsoDateTime(json.getLastModified()));
		ts.setCreatedDate(DateHelper.parseIsoDateTime(json.getCreated()));
		// NOTE don't use a formatter that used TZ.. just want the date as is without conversion.
		ts.setDate(DateHelper.toDateNoTZ(json.getDate(), DateHelper.MYSQL_DATE_PATTERN));
		
		/*
		 *  set the calculated properties
		 */
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");
		DateTimeFormatter dateFmt = DateTimeFormat.forPattern("MM/dd/yyyy");
		DateTimeFormatter dayFmt = DateTimeFormat.forPattern("E");
		DateTimeFormatter zoneFmt = DateTimeFormat.forPattern("ZZ");
		DateTimeFormatter timeFmt = DateTimeFormat.forPattern("h:mm:ss a");
		DateTimeFormatter monthFmt = DateTimeFormat.forPattern("yyyy-MM '('MMM')'");
		
		DateTime tsDate = null;
		if (ts.getType().trim().toLowerCase().equals("manual")) {
//			logger.debug("Manual entry.");
//			logger.debug("Original JSON:" + json.getDate());
			if (ts.getDate() != null) {
//				logger.debug("Date:" + ts.getDate());
				// calculate the zone by the tz integer
				DateTimeZone zone = DateHelper.getZone(ts.getTimeZoneNum());
//				logger.debug("Zone:" + zone);
				// use the date as the start date
//				tsDate = new DateTime(ts.getDate());
				tsDate = new LocalDateTime(ts.getDate()).toDateTime();//.toDateTime(zone); // <-- don't need the zone anymore and it converts when calling to Date();
//				logger.debug("new date:" + tsDate);
				// set the zone str
				ts.setTimeZone(zone.getID()); 
				// add zone
//				tsDate = tsDate.withZone(zone); // <-- this is applying the timezone to the date, thus changing values
				// set the date
				ts.setLocalDate(tsDate.toDate());
//				logger.debug("local_date:" + ts.getLocalDate());
				ts.setLocalDay(tsDate.toString(dayFmt));
				
				// set the month
				ts.setMonth(tsDate.toString(monthFmt)); // extended calculated property
				
				// set the start time - assume 8AM
				LocalTime localTime = new LocalTime(8,0,0,0);
				tsDate = tsDate.withTime(localTime);
				ts.setLocalStartTime(tsDate.toDate());
				
				// add hours to the local start time (to calculate the end time)
				localTime = localTime.plusSeconds((int)ts.getDuration());
				tsDate = tsDate.withTime(localTime);
				ts.setLocalEndTime(tsDate.toDate());				
			}
		}
		else {
			// TODO this needs to move into the else
			if (ts.getStart() != null && !ts.getStart().equals("")) {
				DateTimeZone zone = DateHelper.getZone(ts.getStart());
				fmt = fmt.withZone(zone);
				
				tsDate = DateTime.parse(ts.getStart(), fmt);
				LocalDate localDate = tsDate.toLocalDate();
				LocalDateTime localDateTime = tsDate.toLocalDateTime();
//				logger.debug("Local DateTime:" + localDateTime.toLocalDate());
				ts.setLocalDate(localDate.toDate());	//
				ts.setLocalDay(localDate.toString(dayFmt));
//				ts.setLocalStartTime(tsDate.toString(timeFmt));
				
				ts.setLocalStartTime(localDateTime.toDate()); //
//				logger.debug("Local Start Time:" + ts.getLocalStartTime());
				if (zoneFmt != null) {
					ts.setTimeZone(tsDate.toString(zoneFmt));
				}
				ts.setMonth(tsDate.toString(monthFmt)); // extended calculated property
			}
			
			// if (timeZone starts with ts)
			// calculate the correct timezone str
			
			if (ts.getEnd() != null && !ts.getEnd().equals("")) {
				DateTimeZone zone = DateHelper.getZone(ts.getEnd());
				fmt = fmt.withZone(zone);
				
//				ts.setLocalEndTime(DateTime.parse(ts.getEnd(), fmt).toString(timeFmt));
				
				tsDate = DateTime.parse(ts.getEnd(), fmt);
				LocalDateTime localDateTime = tsDate.toLocalDateTime();
				ts.setLocalEndTime(localDateTime.toDate());
//				ts.setLocalEndTime(DateTime.parse(ts.getEnd(), fmt).toDate());
			}			
		}
				
		// set the hours
		BigDecimal hours = BigDecimal.valueOf(ts.getDuration()/3600D).setScale(5, RoundingMode.HALF_UP);
//		logger.debug("Hours:" + hours);
		ts.setHours(hours.doubleValue());
		
		/*
		 * additional properties such as isManager will have to be setup outside of the is method by an external method
		 * since TimesheetJson only provides the UserID, and a db call has to be performed in ordre to retrieve this data.
		 */
		
		return ts;
	}

	/**
	 * 
	 * @param timesheet
	 * @param user
	 * @return
	 */
	public Timesheet mapFromJson(Timesheet timesheet, User user) {

		// TODO do further interrogations
		/*
		 * forces the group value to remain to it's original non-null value
		 */
		if (timesheet != null && timesheet.getGroup() != null) {
			this.setGroup(timesheet.getGroup());
			return this;
		}
		
		// set the group
		if (this.getGroup() == null) {
			this.setGroup(user.getGroup());

			// determine whether a user is a manager
			if (user.getGroup() != null && user.getGroup().getName().equalsIgnoreCase("managers")) {
				this.setManager(true);
			}
			else {
				this.setManager(false);
			}
		}
		return this;
	}
	
	/**
	 * Map in all the custom fields and values
	 * @param vals
	 * @return
	 */
	public Timesheet mapFromJson(List<CustomFieldValue> vals) {
		for (CustomFieldValue val : vals) {
			String code = "";
			if (val.getCustomField() != null) {
				if (val.getCustomField().getShortCode() != null) {
					code = val.getCustomField().getShortCode().trim().toLowerCase();
//					logger.debug("short code:" + code + "; value:" + val.getValue());
				}
				else {
					code = val.getCustomField().getName();
				}
			}
			else {
				logger.debug("Timesheet doesnt have custom fields:" + this.getId());
			}
			
			if (code.equals("")) return this;

			if (code.equals("actvty")) setActivity(val.getValue());
			else if (code.equals("atvty1")) setActivity1(val.getValue());
			else if (code.equals("atvty2")) setActivity2(val.getValue());
			else if (code.equals("atvty3")) setActivity3(val.getValue());
			else if (code.equals("atvty4")) setActivity4(val.getValue());
			else if (code.equals("atvty5")) setActivity5(val.getValue());
			else if (code.equals("qnty")) setQuantity(val.getValue());
			else if (code.equals("rr")) setRemotelyResolved(val.getValue());
			else if (code.equals("rpa")) setResumePriorActivity(val.getValue());
		}
		
		return this;
	}
	
	/**
	 * 
	 * @param timesheet The persisted timesheet to test against. Since custom fields aren't provided from the service,
	 * these properties aren't set in the object yet.
	 * @param map
	 * @return
	 */
	public Timesheet mapCustomFieldsFromMap(Timesheet timesheet, Map<String, String> map) {
		for (Map.Entry<String, String> customFieldEntry : map.entrySet()) {
			if (User.customFields.containsKey(customFieldEntry.getKey())) {
				/*
				 * NOTE not the best implementation (very hard-coded)
				 */
				String fieldName = User.customFields.get(customFieldEntry.getKey());
//				logger.debug("User Custom Field: Field name: " + fieldName);
				if (fieldName.equals(User.CUSTOM_FIELD_SUPPORT_TYPE)) {
					if (timesheet == null || timesheet.getSupportType() == null || timesheet.getSupportType().equals("")) {
						setSupportType(customFieldEntry.getValue());
					}
					else {
						setSupportType(timesheet.getSupportType());
					}
				}
				else if (fieldName.equals(User.CUSTOM_FIELD_ROLE)) {
					if (timesheet == null || timesheet.getRole() == null || timesheet.getRole().equals("")) {
						setRole(customFieldEntry.getValue());
					}
					else {
						setRole(timesheet.getRole());
					}
				}
			}
		}
		return this;
	}
	
	/**
	 * 
	 */
	public void updateCalcs() {
		/*
		 * set the source
		 */
		String location = this.getLocation().toLowerCase();
		if (location.equals("tsheets android app")
				|| location.equals("smart phone")
				|| location.equals("tsheets iphone app")) {
			setSource("Smartphone");
		}
		else if (location.contains("tsheets") || location.contains("smart phone")) {
			setSource("Mixed");
		}
		else {
			setSource("Web");
		}
		
		// convert quantity to numeric
		Integer quantity = 0;
		
		/*
		 * set all activities
		 */
		try {
			quantity = Integer.valueOf(getQuantity());
			if (quantity > 0) {
				setAllActivities(quantity);
			}
			else setAllActivities(1);
		}
		catch(NumberFormatException e) {
			setAllActivities(1);
		}
		
		/*
		 * set real events
		 */
		String resume = getResumePriorActivity() != null ? getResumePriorActivity().toLowerCase() : "";
		if (resume.equals("yes")) {
			setRealEvents(0);
		}
		else if (quantity > 1) {
			setRealEvents(quantity);
		}
		else {
			setRealEvents(1);
		}
		
		/*
		 * real hours
		 */
//		if (getHours() != null && getRealEvents() > 0) {
		if (getHours() != null && getAllActivities() > 0) {
			BigDecimal hours = BigDecimal.valueOf(getHours()).setScale(5, RoundingMode.HALF_UP);
//			logger.debug(("hours:" + hours));
//			BigDecimal events = BigDecimal.valueOf(getRealEvents().doubleValue()).setScale(5, RoundingMode.HALF_UP);
			BigDecimal activities = BigDecimal.valueOf(getAllActivities().doubleValue()).setScale(5, RoundingMode.HALF_UP);

			//			logger.debug("events:" + events);
			BigDecimal realHours = hours.divide(activities, RoundingMode.HALF_UP).setScale(5, RoundingMode.HALF_UP);
//			logger.debug("real hours:" + realHours);
			setRealHours(realHours.doubleValue());
//			logger.debug("real hours prop:" + getRealHours());
		}
		else {
			setRealHours(new Double(0));
		}

		/*
		 * resumed
		 */
		if (resume.equals("yes")) {
			if (quantity > 1) {
				setResumed(quantity);
			}
			else {
				setResumed(1);
			}		
		}
		else {
			setResumed(0);
		}
		
		/*
		 * exclude
		 */
		if (getRealHours() == 0.0 || getRealHours() > 10.0) 
			setExclude(true);
		else
			setExclude(false);
		
		/*
		 * erroneous entry
		 */
		if (getRealHours() == 0) {
			setErrEntry("= 0");
		}
		else if (getRealHours() < 0.084) {
			setErrEntry("< 5 mins");
		}
		else if (getRealHours() >= 7.0) {
			setErrEntry(">= 7 hours");
		}
		else if (getRealHours() >= 5.0) {
			setErrEntry(">= 5 hours");
		}
		else {
			setErrEntry("normal record");
		}
		
		/*
		 * err entry general
		 */
		if (getErrEntry().equals("normal record")) {
			setErrEntryGeneral("Normal record");
		}
		else if (getErrEntry().equals("=0")) {
			setErrEntryGeneral("Erroneous");
		}
		else if (getErrEntry().equals(">= 7 hours")) {
			setErrEntryGeneral("Erroneous");
		}
		else {
			setErrEntryGeneral("Needs review");
		}
		
		Calendar cl = Calendar. getInstance();
//		logger.debug("local date:" + getLocalDate());
		if (getLocalDate() != null && !getLocalDate().equals("")) {
//		    cl.setTime(new Date(getLocalDate()));
			cl.setTime(getLocalDate());
			
			/*
			 * week num
			 */
		    setWeekNum(cl.get(Calendar.WEEK_OF_MONTH));
			
			/*
			 * week day
			 */
		    int day = cl.get(Calendar.DAY_OF_WEEK);
		    setWeekDay(DayOfWeek.of(day).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
		    
			/*
			 * work day
			 */
			if (getWeekDay().equalsIgnoreCase("sat")
					|| getWeekDay().equalsIgnoreCase("sun")) {
				setWorkDay("weekend");
			}
			else {
				setWorkDay("workday");
			}
		}
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the locked
	 */
	public int getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(int locked) {
		this.locked = locked;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the onTheClock
	 */
	public boolean isOnTheClock() {
		return onTheClock;
	}

	/**
	 * @param onTheClock the onTheClock to set
	 */
	public void setOnTheClock(boolean onTheClock) {
		this.onTheClock = onTheClock;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the jobCode
	 */
	public JobCode getJobCode() {
		return jobCode;
	}

	/**
	 * @param jobCode the jobCode to set
	 */
	public void setJobCode(JobCode jobCode) {
		this.jobCode = jobCode;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the customFieldValue
	 */
	public List<CustomFieldValue> getCustomFieldValue() {
		if (customFieldValue == null) {
			customFieldValue = new ArrayList<CustomFieldValue>();
		}
		return customFieldValue;
	}

	/**
	 * @param customFieldValue the customFieldValue to set
	 */
	public void setCustomFieldValue(List<CustomFieldValue> customFieldValue) {
		this.customFieldValue = customFieldValue;
	}

	/**
	 * @return the localDate
	 */
	public Date getLocalDate() {
		return localDate;
	}

	/**
	 * @param localDate the localDate to set
	 */
	public void setLocalDate(Date localDate) {
		this.localDate = localDate;
	}

	/**
	 * @return the localDay
	 */
	public String getLocalDay() {
		return localDay;
	}

	/**
	 * @param localDay the localDay to set
	 */
	public void setLocalDay(String localDay) {
		this.localDay = localDay;
	}

	/**
	 * @return the localStartTime
	 */
	public Date getLocalStartTime() {
		return localStartTime;
	}

	/**
	 * @param localStartTime the localStartTime to set
	 */
	public void setLocalStartTime(Date localStartTime) {
		this.localStartTime = localStartTime;
	}

	/**
	 * @return the localEndTime
	 */
	public Date getLocalEndTime() {
		return localEndTime;
	}

	/**
	 * @param localEndTime the localEndTime to set
	 */
	public void setLocalEndTime(Date localEndTime) {
		this.localEndTime = localEndTime;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the hours
	 */
	public Double getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Double hours) {
		this.hours = hours;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the allActivities
	 */
	public Integer getAllActivities() {
		return allActivities;
	}

	/**
	 * @param allActivities the allActivities to set
	 */
	public void setAllActivities(Integer allActivities) {
		this.allActivities = allActivities;
	}

	/**
	 * @return the realEvents
	 */
	public Integer getRealEvents() {
		return realEvents;
	}

	/**
	 * @param realEvents the realEvents to set
	 */
	public void setRealEvents(Integer realEvents) {
		this.realEvents = realEvents;
	}

	/**
	 * @return the realHours
	 */
	public Double getRealHours() {
		return realHours;
	}

	/**
	 * @param realHours the realHours to set
	 */
	public void setRealHours(Double realHours) {
		this.realHours = realHours;
	}

	/**
	 * @return the resumed
	 */
	public Integer getResumed() {
		return resumed;
	}

	/**
	 * @param resumed the resumed to set
	 */
	public void setResumed(Integer resumed) {
		this.resumed = resumed;
	}

	/**
	 * @return the exclude
	 */
	public Boolean getExlude() {
		return exclude;
	}

	/**
	 * @param exclude the exclude to set
	 */
	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
	}

	/**
	 * @return the errEntry
	 */
	public String getErrEntry() {
		return errEntry;
	}

	/**
	 * @param errEntry the errEntry to set
	 */
	public void setErrEntry(String erroneousEntry) {
		this.errEntry = erroneousEntry;
	}

	/**
	 * @return the errEntryGeneral
	 */
	public String getErrEntryGeneral() {
		return errEntryGeneral;
	}

	/**
	 * @param errEntryGeneral the errEntryGeneral to set
	 */
	public void setErrEntryGeneral(String errEntryGeneral) {
		this.errEntryGeneral = errEntryGeneral;
	}

	/**
	 * @return the weekNum
	 */
	public Integer getWeekNum() {
		return weekNum;
	}

	/**
	 * @param weekNum the weekNum to set
	 */
	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	/**
	 * @return the weekDay
	 */
	public String getWeekDay() {
		return weekDay;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	/**
	 * @return the workDay
	 */
	public String getWorkDay() {
		return workDay;
	}

	/**
	 * @param workDay the workDay to set
	 */
	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}

	/**
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * @param week the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * @return the manager
	 */
	public Boolean getManager() {
		return manager;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean isManager() {
		return getManager();
	}
	
	/**
	 * @param manager the manager to set
	 */
	public void setManager(Boolean manager) {
		this.manager = manager;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the activity1
	 */
	public String getActivity1() {
		return activity1;
	}

	/**
	 * @param activity1 the activity1 to set
	 */
	public void setActivity1(String activity1) {
		this.activity1 = activity1;
	}

	/**
	 * @return the activity2
	 */
	public String getActivity2() {
		return activity2;
	}

	/**
	 * @param activity2 the activity2 to set
	 */
	public void setActivity2(String activity2) {
		this.activity2 = activity2;
	}

	/**
	 * @return the activity3
	 */
	public String getActivity3() {
		return activity3;
	}

	/**
	 * @param activity3 the activity3 to set
	 */
	public void setActivity3(String activity3) {
		this.activity3 = activity3;
	}

	/**
	 * @return the activity4
	 */
	public String getActivity4() {
		return activity4;
	}

	/**
	 * @param activity4 the activity4 to set
	 */
	public void setActivity4(String activity4) {
		this.activity4 = activity4;
	}

	/**
	 * @return the activity5
	 */
	public String getActivity5() {
		return activity5;
	}

	/**
	 * @param activity5 the activity5 to set
	 */
	public void setActivity5(String activity5) {
		this.activity5 = activity5;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the remotelyResolved
	 */
	public String getRemotelyResolved() {
		return remotelyResolved;
	}

	/**
	 * @param remotelyResolved the remotelyResolved to set
	 */
	public void setRemotelyResolved(String remotelyResolved) {
		this.remotelyResolved = remotelyResolved;
	}

	/**
	 * @return the resumePriorActivity
	 */
	public String getResumePriorActivity() {
		return resumePriorActivity;
	}

	/**
	 * @param resumePriorActivity the resumePriorActivity to set
	 */
	public void setResumePriorActivity(String resumePriorActivity) {
		this.resumePriorActivity = resumePriorActivity;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the timeZoneNum
	 */
	public Double getTimeZoneNum() {
		return timeZoneNum;
	}

	/**
	 * @param timeZoneNum the timeZoneNum to set
	 */
	public void setTimeZoneNum(Double timeZoneNum) {
		this.timeZoneNum = timeZoneNum;
	}

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean isDeleted() {
		return getDeleted();
	}
	
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Timesheet [id=" + id + ", locked=" + locked + ", deleted=" + deleted + ", notes=" + notes
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", type=" + type
				+ ", onTheClock=" + onTheClock + ", start=" + start + ", end=" + end + ", date=" + date + ", duration="
				+ duration + ", location=" + location + ", user=" + user + ", group=" + group + ", jobCode=" + jobCode
				+ ", customFieldValue=" + customFieldValue + ", supportType=" + supportType + ", localDate=" + localDate
				+ ", localDay=" + localDay + ", localStartTime=" + localStartTime + ", localEndTime=" + localEndTime
				+ ", timeZone=" + timeZone + ", timeZoneNum=" + timeZoneNum + ", hours=" + hours + ", source=" + source
				+ ", month=" + month + ", allActivities=" + allActivities + ", realEvents=" + realEvents
				+ ", realHours=" + realHours + ", resumed=" + resumed + ", exclude=" + exclude + ", errEntry="
				+ errEntry + ", errEntryGeneral=" + errEntryGeneral + ", weekNum=" + weekNum + ", weekDay=" + weekDay
				+ ", workDay=" + workDay + ", manager=" + manager + ", activity=" + activity + ", activity1="
				+ activity1 + ", activity2=" + activity2 + ", activity3=" + activity3 + ", activity4=" + activity4
				+ ", activity5=" + activity5 + ", quantity=" + quantity + ", remotelyResolved=" + remotelyResolved
				+ ", resumePriorActivity=" + resumePriorActivity + ", week=" + week + "]";
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the supportType
	 */
	public String getSupportType() {
		return supportType;
	}

	/**
	 * @param supportType the supportType to set
	 */
	public void setSupportType(String supportType) {
		this.supportType = supportType;
	}


	
//
//	/**
//	 * @return the customFields
//	 */
//	public List<CustomField> getCustomFields() {
//		if (customFields == null) {
//			customFields = new ArrayList<CustomField>();
//		}
//		return customFields;
//	}
//
//	/**
//	 * @param customFields the customFields to set
//	 */
//	public void setCustomFields(List<CustomField> customFields) {
//		this.customFields = customFields;
//	}
	
}
