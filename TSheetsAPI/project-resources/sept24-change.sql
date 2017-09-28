-- add a new column named role to timesheet
alter table tsheets_timesheet
add [role] varchar(25);

-- drop supportType
alter table tsheets_timesheet
drop column supportType;

-- alter the supportType column to timesheet
alter table tsheets_timesheet
add support_type varchar(50);

-- add a new column named role to user
alter table tsheets_user
add [role] varchar(25);

-- alter the supportType column to user
alter table tsheets_user
add support_type varchar(50);

-- alter the custom_field_values table
alter table tsheets_custom_field_value
add [user_id] [numeric](19, 0) NULL;

-- add foreign key constraint
ALTER TABLE [dbo].[TSHEETS_CUSTOM_FIELD_VALUE]  WITH CHECK ADD  CONSTRAINT [FK_TSHEETS_CUSTOM_FIELD_VALUE_TSHEETS_USER] FOREIGN KEY([user_id])
REFERENCES [dbo].[TSHEETS_USER] ([id]);

ALTER TABLE [dbo].[TSHEETS_CUSTOM_FIELD_VALUE] CHECK CONSTRAINT [FK_TSHEETS_CUSTOM_FIELD_VALUE_TSHEETS_USER]

