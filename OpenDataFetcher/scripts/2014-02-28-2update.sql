ALTER TABLE `total_recall`.`subscribe` 
ADD COLUMN `unsubscribed` TINYINT NOT NULL DEFAULT 0 AFTER `dataset_id`;