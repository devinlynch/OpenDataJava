ALTER TABLE `total_recall`.`subscribe_assertion` 
ADD INDEX `subscribe_assertion_ibfk_2_idx` (`dataset_input_id` ASC);
ALTER TABLE `total_recall`.`subscribe_assertion` 
ADD CONSTRAINT `subscribe_assertion_ibfk_2`
  FOREIGN KEY (`dataset_input_id`)
  REFERENCES `total_recall`.`dataset_input` (`dataset_input_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `total_recall`.`subscribe` 
ADD COLUMN `last_processed_date` DATETIME NULL AFTER `unsubscribed`,
ADD COLUMN `should_look_at_past_data` TINYINT NOT NULL DEFAULT 0 AFTER `last_processed_date`;

ALTER TABLE `total_recall`.`dataset` 
ADD COLUMN `last_process` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP AFTER `email_content`;

ALTER TABLE `total_recall`.`subscribe` 
ADD COLUMN `created_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP AFTER `should_look_at_past_data`;


ALTER TABLE `total_recall`.`dataset_value` 
ADD UNIQUE INDEX `dataset_value_un_ind_1` (`dataset_record_id` ASC, `dataset_input_id` ASC);

ALTER TABLE `total_recall`.`dataset_record` 
ADD COLUMN `external_id` VARCHAR(150) NULL AFTER `creation_date`;
