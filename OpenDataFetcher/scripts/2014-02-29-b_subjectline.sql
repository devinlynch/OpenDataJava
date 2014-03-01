ALTER TABLE `total_recall`.`dataset` 
CHANGE COLUMN `email_content` `email_content` VARCHAR(2000) NULL DEFAULT NULL ,
ADD COLUMN `subject_content` VARCHAR(100) NULL AFTER `last_process`;


ALTER TABLE `total_recall`.`subscribe_notified` 
ADD COLUMN `content` TEXT NULL AFTER `notification_date`,
ADD COLUMN `subject` VARCHAR(100) NULL AFTER `content`,
ADD COLUMN `sent` TINYINT NOT NULL DEFAULT 0 AFTER `subject`;


ALTER TABLE `total_recall`.`dataset` 
CHANGE COLUMN `last_process` `last_process` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ;
