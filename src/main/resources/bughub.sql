/*
SQLyog Trial v13.1.1 (64 bit)
MySQL - 8.0.19 : Database - bughub
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bughub` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bughub`;

/*Table structure for table `bug` */

DROP TABLE IF EXISTS `bug`;

CREATE TABLE `bug` (
  `id` char(32) NOT NULL COMMENT '主键',
  `state` varchar(10) DEFAULT NULL COMMENT '状态',
  `level` int DEFAULT NULL COMMENT '级别',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `userId` char(32) DEFAULT NULL COMMENT '创建人',
  `userName` varchar(50) DEFAULT NULL COMMENT '创建人',
  `projectId` char(32) DEFAULT NULL COMMENT '项目标识',
  `projectName` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `projectVersion` varchar(50) DEFAULT NULL COMMENT '项目版本',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bug` */

/*Table structure for table `bug_track` */

DROP TABLE IF EXISTS `bug_track`;

CREATE TABLE `bug_track` (
  `id` char(32) NOT NULL COMMENT '主键',
  `bugId` char(32) DEFAULT NULL COMMENT 'BUG',
  `state` varchar(50) DEFAULT NULL COMMENT '状态',
  `title` varchar(100) DEFAULT NULL COMMENT '概要',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `version` varchar(50) DEFAULT NULL COMMENT '对应版本',
  `userId` char(32) DEFAULT NULL COMMENT '创建人',
  `userName` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `bugId` (`bugId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bug_track` */

/*Table structure for table `bug_user` */

DROP TABLE IF EXISTS `bug_user`;

CREATE TABLE `bug_user` (
  `id` char(32) NOT NULL COMMENT '主键',
  `bugId` char(32) DEFAULT NULL COMMENT 'BUG标识',
  `userId` char(32) DEFAULT NULL COMMENT '用户标识',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `fromUserId` char(32) DEFAULT NULL COMMENT '分配用户',
  `fromUserName` varchar(50) DEFAULT NULL COMMENT '分配用户',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` tinyint(1) DEFAULT NULL COMMENT '是否是创建人',
  PRIMARY KEY (`id`),
  KEY `bugId` (`bugId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bug_user` */

/*Table structure for table `hub_file` */

DROP TABLE IF EXISTS `hub_file`;

CREATE TABLE `hub_file` (
  `id` char(32) NOT NULL COMMENT '主键',
  `owner` char(32) DEFAULT NULL COMMENT '所属主体',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `size` int DEFAULT NULL COMMENT '文件大小',
  `objectName` varchar(255) DEFAULT NULL COMMENT '对象名称',
  `contentType` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `userId` char(32) DEFAULT NULL COMMENT '上传用户',
  `userName` varchar(50) DEFAULT NULL COMMENT '上传用户名',
  `createTime` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`),
  KEY `owner` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `hub_file` */

/*Table structure for table `hub_user` */

DROP TABLE IF EXISTS `hub_user`;

CREATE TABLE `hub_user` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `roles` varchar(255) DEFAULT NULL COMMENT '角色',
  `state` int DEFAULT NULL COMMENT '状态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `hub_user` */

insert  into `hub_user`(`id`,`name`,`account`,`password`,`roles`,`state`,`createTime`,`updateTime`) values 
('3340fbbde7564fd09683c649c8267bba','蒋华','jiangh','111111','PM',1,'2020-03-16 21:20:56','2020-03-16 21:20:56'),
('35fb5979b26247119b4f4e6533be708d','胡佳俊','hujj','111111','DEVELOPER',1,'2020-03-16 21:21:22','2020-03-16 21:21:22'),
('81ea0374e77e468ab2357903312b1b8d','高华建','gaohj','111111','DEVELOPER',1,'2020-03-16 21:22:19','2020-03-16 21:22:19'),
('8269f4cebf4a4c1b880827680db13837','孙瑶','sunyao','111111','DEVELOPER',1,'2020-03-16 21:21:55','2020-03-16 21:21:55'),
('83598a413fe44f46971c5aaa983aad9e','测试用户','test','test','TESTER',1,'2020-03-09 17:41:57','2020-03-09 17:41:57'),
('85edc7fb5e9f46b29cd77f9696edd3c3','段百川','duanbc','111111','DEVELOPER',1,'2020-03-16 21:21:38','2020-03-16 21:21:38'),
('b5a17e3d6b6844a4a7b3341efd439ef3','飞龙','feilong','feilong','DEVELOPER',1,'2020-03-11 21:03:27','2020-03-11 21:03:27'),
('b5a50c49af4a4d24b9703811b5a55cf2','李谊成','liyc','111111','TESTER',1,'2020-03-16 21:19:35','2020-03-16 21:19:35'),
('eb76ca7b8d3549a9b21955c7001ee676','顾芳平','gufp','111111','PM',1,'2020-03-16 21:20:35','2020-03-16 21:20:35'),
('f07bcdd3327f4165a62487587d876fc9','管理员','admin','admin','ADMIN',1,'2020-02-29 20:40:04','2020-03-09 15:05:16'),
('f0d0dddecedf45f8881684c4f193f2bb','马言平','mayp','111111','TESTER',1,'2020-03-16 21:19:55','2020-03-16 21:19:55'),
('f8ae541ac0ed49a8b77b57928a21c4e0','田奇','super','superman','ADMIN',1,'2020-02-26 14:19:49','2020-03-09 15:05:09'),
('fd99c98946714b5dbdd5103126e6d10a','徐晨','xuchen','xuchen','DEVELOPER',1,'2020-03-10 19:51:46','2020-03-10 19:51:46');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `state` int DEFAULT NULL COMMENT '状态',
  `userId` char(32) DEFAULT NULL COMMENT '操作人',
  `userName` varchar(50) DEFAULT NULL COMMENT '操作人姓名',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project` */

insert  into `project`(`id`,`name`,`description`,`state`,`userId`,`userName`,`createTime`,`updateTime`) values 
('08f3705c5a0211ea86c952549ac5e67a','Bughub','Bug管理系统',1,'f07bcdd3327f4165a62487587d876fc9','管理员','2020-02-28 16:11:32','2020-03-23 21:47:39'),
('3962f4955a0a11ea86c952549ac5e67a','SQL-Cloud','数据库云客户端',1,'f8ae541ac0ed49a8b77b57928a21c4e0','田奇','2020-02-28 17:11:44','2020-03-15 23:14:54'),
('4e2663a65a0a11ea86c952549ac5e67a','qywx-sdk','企业微信Java版SDK',1,'f8ae541ac0ed49a8b77b57928a21c4e0','田奇','2020-02-28 17:11:48','2020-03-15 22:57:02'),
('9ffbc23979bd429e87a6a9fad29a0a03','OA','oa project for springboot',NULL,'f07bcdd3327f4165a62487587d876fc9','管理员','2020-03-09 15:07:52','2020-03-15 23:15:30');

/*Table structure for table `project_user` */

DROP TABLE IF EXISTS `project_user`;

CREATE TABLE `project_user` (
  `id` char(32) NOT NULL COMMENT '主键',
  `userId` char(32) DEFAULT NULL COMMENT '用户',
  `projectId` char(32) DEFAULT NULL COMMENT '项目',
  PRIMARY KEY (`id`),
  KEY `projectId` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project_user` */

insert  into `project_user`(`id`,`userId`,`projectId`) values 
('005d94d6589d40a9abe8f7a5454e9ae5','f0d0dddecedf45f8881684c4f193f2bb','08f3705c5a0211ea86c952549ac5e67a'),
('09effe5eb54f4601bc57be5b1fc563db','f8ae541ac0ed49a8b77b57928a21c4e0','3962f4955a0a11ea86c952549ac5e67a'),
('1f7436fdb35d4fb9a7a203cc9f64fbe9','f07bcdd3327f4165a62487587d876fc9','08f3705c5a0211ea86c952549ac5e67a'),
('32c748e3be5745ec8827a7fcb35006e8','b5a50c49af4a4d24b9703811b5a55cf2','08f3705c5a0211ea86c952549ac5e67a'),
('38c6b5a0e1cc4b37bd57dc05e34b17a7','83598a413fe44f46971c5aaa983aad9e','08f3705c5a0211ea86c952549ac5e67a'),
('3ef6364beaea4ce29baf65d6fd4aeeac','f8ae541ac0ed49a8b77b57928a21c4e0','08f3705c5a0211ea86c952549ac5e67a'),
('5c9718c2a40d454896f3d745f799c0d6','35fb5979b26247119b4f4e6533be708d','08f3705c5a0211ea86c952549ac5e67a'),
('5d597c5e3fdb4cb5874aa23d686e83a0','8269f4cebf4a4c1b880827680db13837','08f3705c5a0211ea86c952549ac5e67a'),
('808af738bb3d46dc8fcb96150305e156','fd99c98946714b5dbdd5103126e6d10a','08f3705c5a0211ea86c952549ac5e67a'),
('89fb6b5ccc4b4ee6a4e0ab209bfaf205','f8ae541ac0ed49a8b77b57928a21c4e0','4e2663a65a0a11ea86c952549ac5e67a'),
('a16e082de10c455fb3b0a62b68c0ea0c','b5a17e3d6b6844a4a7b3341efd439ef3','9ffbc23979bd429e87a6a9fad29a0a03'),
('a6b6316019fd4d329d0368ff4a6532b8','3340fbbde7564fd09683c649c8267bba','08f3705c5a0211ea86c952549ac5e67a'),
('a8c98c82c50248e59fdebe9d691ba77f','b5a17e3d6b6844a4a7b3341efd439ef3','08f3705c5a0211ea86c952549ac5e67a'),
('bddb8851e1244e80b5a93de6d901f536','f8ae541ac0ed49a8b77b57928a21c4e0','9ffbc23979bd429e87a6a9fad29a0a03'),
('c2cf29c337a84295a78ede20eb060fca','fd99c98946714b5dbdd5103126e6d10a','9ffbc23979bd429e87a6a9fad29a0a03'),
('c32fa37cdd5d442ba2d1fb9fd1acf79d','85edc7fb5e9f46b29cd77f9696edd3c3','08f3705c5a0211ea86c952549ac5e67a'),
('cdd904aacff44a2b88dd7a40bbd5c429','81ea0374e77e468ab2357903312b1b8d','08f3705c5a0211ea86c952549ac5e67a'),
('e39b70bc4ac4420a8c1bf2f66b02d57f','fd99c98946714b5dbdd5103126e6d10a','3962f4955a0a11ea86c952549ac5e67a'),
('e3c93783a02a4c96929e29086dc9cd47','eb76ca7b8d3549a9b21955c7001ee676','08f3705c5a0211ea86c952549ac5e67a');

/*Table structure for table `project_user_role` */

DROP TABLE IF EXISTS `project_user_role`;

CREATE TABLE `project_user_role` (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `userId` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户',
  `projectId` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '项目',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`),
  KEY `projectId` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project_user_role` */

insert  into `project_user_role`(`id`,`userId`,`projectId`,`role`) values 
('0303e1995dac4b03bd812b1a72f09fc2','f8ae541ac0ed49a8b77b57928a21c4e0','9ffbc23979bd429e87a6a9fad29a0a03','PM'),
('15d2fcb0ed3a495c8e2d9b9754ac5bae','b5a17e3d6b6844a4a7b3341efd439ef3','08f3705c5a0211ea86c952549ac5e67a','DEVELOPER'),
('2589f933f7c54e79b435e1143c18031c','fd99c98946714b5dbdd5103126e6d10a','9ffbc23979bd429e87a6a9fad29a0a03','DEVELOPER'),
('267d118d454d49b5890bb47ebeb5d973','fd99c98946714b5dbdd5103126e6d10a','9ffbc23979bd429e87a6a9fad29a0a03','TESTER'),
('27e9adc44449456d93d4be87d94e4d9c','3340fbbde7564fd09683c649c8267bba','08f3705c5a0211ea86c952549ac5e67a','PM'),
('5761716ed4ec445495d4dcddc3a3ea06','8269f4cebf4a4c1b880827680db13837','08f3705c5a0211ea86c952549ac5e67a','DEVELOPER'),
('584b2a27295e4815aeb683905c7319bb','f8ae541ac0ed49a8b77b57928a21c4e0','08f3705c5a0211ea86c952549ac5e67a','PM'),
('5be4c72ea39f40168cddf2fe57f6b702','f8ae541ac0ed49a8b77b57928a21c4e0','3962f4955a0a11ea86c952549ac5e67a','DEVELOPER'),
('5e4b904b0693491eb60f9dee487fa930','81ea0374e77e468ab2357903312b1b8d','08f3705c5a0211ea86c952549ac5e67a','DEVELOPER'),
('5e70bc9c366b4d359856501888ea4b27','b5a17e3d6b6844a4a7b3341efd439ef3','9ffbc23979bd429e87a6a9fad29a0a03','TESTER'),
('635738fa87a844d7ac285e6ea10f62d7','f8ae541ac0ed49a8b77b57928a21c4e0','9ffbc23979bd429e87a6a9fad29a0a03','ADMIN'),
('707fcc5de7584d6c899dc0db77f0a0aa','f8ae541ac0ed49a8b77b57928a21c4e0','08f3705c5a0211ea86c952549ac5e67a','DEVELOPER'),
('75c252add3744d54a9103b23e03986a7','f8ae541ac0ed49a8b77b57928a21c4e0','4e2663a65a0a11ea86c952549ac5e67a','PM'),
('8c1e58854d164a779fb981d29fe05840','fd99c98946714b5dbdd5103126e6d10a','08f3705c5a0211ea86c952549ac5e67a','DEVELOPER'),
('8d0dbc836440413a87b236d9d66a382d','b5a50c49af4a4d24b9703811b5a55cf2','08f3705c5a0211ea86c952549ac5e67a','TESTER'),
('8f5fa6edf2314d8fad71f3edf96a30cd','f8ae541ac0ed49a8b77b57928a21c4e0','4e2663a65a0a11ea86c952549ac5e67a','ADMIN'),
('9301836c9b4e4e5281cc4116faccbe61','f07bcdd3327f4165a62487587d876fc9','08f3705c5a0211ea86c952549ac5e67a','TESTER'),
('a1fb3adeef5f41f8ae6f69ca0079c408','85edc7fb5e9f46b29cd77f9696edd3c3','08f3705c5a0211ea86c952549ac5e67a','DEVELOPER'),
('a5a66afa2b244092b222c8799b08062a','fd99c98946714b5dbdd5103126e6d10a','3962f4955a0a11ea86c952549ac5e67a','DEVELOPER'),
('a79ac493d2ca42788d848e6617981053','83598a413fe44f46971c5aaa983aad9e','08f3705c5a0211ea86c952549ac5e67a','TESTER'),
('b4de4a2284474536a40252a7d96413b8','f8ae541ac0ed49a8b77b57928a21c4e0','4e2663a65a0a11ea86c952549ac5e67a','DEVELOPER'),
('b7f2cdb02b554e72a50a11d9d81b7996','35fb5979b26247119b4f4e6533be708d','08f3705c5a0211ea86c952549ac5e67a','DEVELOPER'),
('ba3b04b0654d49d585c22e8c30df61f7','fd99c98946714b5dbdd5103126e6d10a','3962f4955a0a11ea86c952549ac5e67a','TESTER'),
('bd90d5bf915a4bada00d6ae7141feed4','f8ae541ac0ed49a8b77b57928a21c4e0','4e2663a65a0a11ea86c952549ac5e67a','TESTER'),
('c0a65d26c8ed46968440e6153ed4095d','fd99c98946714b5dbdd5103126e6d10a','3962f4955a0a11ea86c952549ac5e67a','PM'),
('cae8d669b4ba4606b1bce2784964eae2','f8ae541ac0ed49a8b77b57928a21c4e0','3962f4955a0a11ea86c952549ac5e67a','ADMIN'),
('d472d78ba01d4bdeb8b868d1e4f3118b','f0d0dddecedf45f8881684c4f193f2bb','08f3705c5a0211ea86c952549ac5e67a','TESTER'),
('e996706be0e549b0bf337167543a040c','eb76ca7b8d3549a9b21955c7001ee676','08f3705c5a0211ea86c952549ac5e67a','PM'),
('fd827ec2c27146b39c048627b5100a47','f8ae541ac0ed49a8b77b57928a21c4e0','08f3705c5a0211ea86c952549ac5e67a','ADMIN'),
('fddd42d903384ec3b914d63245094d35','b5a17e3d6b6844a4a7b3341efd439ef3','08f3705c5a0211ea86c952549ac5e67a','TESTER');

/*Table structure for table `project_version` */

DROP TABLE IF EXISTS `project_version`;

CREATE TABLE `project_version` (
  `id` char(32) NOT NULL COMMENT '主键',
  `version` varchar(50) DEFAULT NULL COMMENT '版本号',
  `projectId` char(32) DEFAULT NULL COMMENT '项目标识',
  `description` varchar(500) DEFAULT NULL COMMENT '版本描述',
  `userId` char(32) DEFAULT NULL COMMENT '发布人',
  `userName` varchar(50) DEFAULT NULL COMMENT '发布人姓名',
  `createTime` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  KEY `projectId` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目发布记录';

/*Data for the table `project_version` */

insert  into `project_version`(`id`,`version`,`projectId`,`description`,`userId`,`userName`,`createTime`) values 
('1361ee4c78e243c39ad6f29878947e18','2020.03.16.21.24','08f3705c5a0211ea86c952549ac5e67a','bughub 1.0 的一切准备工作似乎都已到位。发布之弦，一触即发。\n不枉近百个日日夜夜与之为伴。因小而大，因弱而强。\n无论它能走多远，抑或如何支撑？至少我曾倾注全心，无怨无悔 ','f8ae541ac0ed49a8b77b57928a21c4e0','田奇','2020-03-16 21:24:13');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
