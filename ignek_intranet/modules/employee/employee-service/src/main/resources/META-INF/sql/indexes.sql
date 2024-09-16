create index IX_DD75498A on IgnekIntranet_Employee (emailAddress[$COLUMN_LENGTH:75$]);
create index IX_EC262398 on IgnekIntranet_Employee (userId);
create index IX_E69B2FD2 on IgnekIntranet_Employee (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F67182D4 on IgnekIntranet_Employee (uuid_[$COLUMN_LENGTH:75$], groupId);