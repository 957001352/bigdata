/*==============================================================*/
/* Table: 工厂机构                                       */
/*==============================================================*/
drop table if exists dhlk_basic_org;
create table  dhlk_basic_org (
  id integer NOT NULL AUTO_INCREMENT,
  code varchar(10) DEFAULT NULL COMMENT '机构编码',
  name varchar(20) DEFAULT NULL COMMENT '机构名称',
  parent_id integer DEFAULT '0' COMMENT '父机构 0最顶级机构',
  status integer DEFAULT '0' COMMENT '状态 0正常 2 删除',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工厂管理';
/*==============================================================*/
/* Table: 访问秘钥                                   */
/*==============================================================*/
drop table if exists dhlk_basic_org_auth;
create table dhlk_basic_org_auth
(
    id                   integer                        NOT NULL AUTO_INCREMENT,
    auth                 varchar(100)                   DEFAULT NULL COMMENT '秘钥',
    org                  varchar(50)                    DEFAULT NULL COMMENT '厂区',
    primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访问秘钥';
/*==============================================================*/
/* Table: 网络设备                            */
/*==============================================================*/
drop table if exists dhlk_basic_net_devices;
create table dhlk_basic_net_devices 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(20)                    DEFAULT NULL COMMENT '名称',
   ip                   varchar(50)                    DEFAULT NULL COMMENT 'ip地址',
   gateway              varchar(50)                    DEFAULT NULL COMMENT '默认网关',
   mask                 varchar(50)                    DEFAULT NULL COMMENT '子网掩码',
   create_time          timestamp                      DEFAULT NOW() COMMENT '创建时间',
   status               integer                        DEFAULT '0' COMMENT '状态 0正常 1停用',
   license              varchar(20)                    DEFAULT NULL COMMENT '许可',
   type_id              integer                        DEFAULT '1' COMMENT '设备类型  1大数据一体机 2 BI控制器',
   note                 varchar(200)                   DEFAULT NULL COMMENT '设备描述',
   factory_id           integer                        DEFAULT NULL COMMENT '集团id',
   tb_id                varchar(100)                   DEFAULT NULL COMMENT 'tb id',
   credentials          varchar(100)                   DEFAULT NULL COMMENT '网络设备访问令牌token',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网络设备';


/*==============================================================*/
/* Table: 网络设备故障                                          */
/*==============================================================*/
drop table if exists dhlk_basic_net_fault;
create table dhlk_basic_net_fault
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   tb_id                varchar(100)                   DEFAULT NULL COMMENT 'td网络设备Id',
   type                 varchar(100)                   DEFAULT NULL COMMENT '故障类型',
   content              text                           DEFAULT NULL COMMENT '故障信息',
   status               integer                        DEFAULT '0' COMMENT '状态 0待处理 1已处理',
   create_time          timestamp                      DEFAULT NOW() COMMENT '创建时间',
   deal_user            integer                        DEFAULT NULL COMMENT '处理人',
   deal_time            timestamp                      DEFAULT NOW() COMMENT '处理时间',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网络设备故障';

/*==============================================================*/
/* Table: 生产设备                          */
/*==============================================================*/
drop table if exists dhlk_basic_product_devices;
create table dhlk_basic_product_devices 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(20)                    DEFAULT NULL COMMENT '设备名称',
   org_id               integer                        DEFAULT NULL COMMENT '所属车间',
   note                 varchar(200)                   DEFAULT NULL COMMENT '描述',
   factory_id           integer                        DEFAULT NULL COMMENT '所属集团',
   classify_id          varchar(100)                   DEFAULT NULL COMMENT '设备分类',
   create_time          timestamp                      DEFAULT NOW() COMMENT '添加时间',
   status               integer                        DEFAULT '0' COMMENT '状态  0正常 1禁用 2 删除',
   tb_id                varchar(100)                   DEFAULT NULL COMMENT 'tdId',
   credentials          varchar(100)                   DEFAULT NULL COMMENT '生产设备访问令牌token',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='生产设备';

/*==============================================================*/
/* Table: 生产设备和网络设备关系                             */
/*==============================================================*/
drop table if exists dhlk_basic_product_net;
create table dhlk_basic_product_net
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   net_id               integer                        DEFAULT NULL COMMENT '设备分类',
   product_id           integer                        DEFAULT NULL COMMENT '设备分类',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='生产设备和网络设备关系配置';

/*==============================================================*/
/* Table: 软件网络设备关系表                        */
/*==============================================================*/
drop table if exists dhlk_basic_net_devices_soft;
create table dhlk_basic_net_devices_soft 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(50)                    DEFAULT NULL COMMENT '软件名称',
   version              varchar(10)                    DEFAULT NULL COMMENT '当前版本号',
   url                  varchar(200)                   DEFAULT NULL COMMENT '云端地址url',
   port                 varchar(10)                    DEFAULT NULL COMMENT '端口号',
   net_device_id        integer                        DEFAULT NULL COMMENT '网络设备',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='软件网络设备关系表';

/*==============================================================*/
/* Table: 用户                                     */
/*==============================================================*/
drop table if exists dhlk_basic_user;
create table dhlk_basic_user 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(50)                    DEFAULT NULL COMMENT '姓名',
   login_name           varchar(50)                    DEFAULT NULL COMMENT '用户名',
   password             varchar(100)                   DEFAULT NULL COMMENT '密码',
   phone                varchar(50)                    DEFAULT NULL COMMENT '联系电话',
   email                varchar(50)                    DEFAULT NULL COMMENT '邮箱',
   status               integer                        DEFAULT '0' COMMENT '状态  0正常 1禁用',
   org_id               varchar(100)                   DEFAULT NULL COMMENT '所属机构集合 用逗号分割',
   create_time          timestamp                      DEFAULT NOW() COMMENT '创建时间',
   is_admin             integer                        DEFAULT '1' COMMENT '是否超级用户',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

/*==============================================================*/
/* Table: mosquito                                */
/*==============================================================*/
drop table if exists dhlk_basic_data_broker;
create table dhlk_basic_data_broker 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name        			varchar(50)                    DEFAULT NULL COMMENT '名称',
   ip                   varchar(50)                    DEFAULT NULL COMMENT 'ip地址',
   topic_list           varchar(1000)                  DEFAULT NULL COMMENT '主题',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据订阅';

/*==============================================================*/
/* Table: 用户登录日志                                 */
/*==============================================================*/
drop table if exists dhlk_basic_login_log;
create table dhlk_basic_login_log 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   user_id              integer                        DEFAULT NULL COMMENT '登录用户',
   ip                   varchar(50)                    DEFAULT NULL COMMENT '登录地址',
   login_time           timestamp                      DEFAULT NOW() COMMENT '登录时间',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录日志';

/*==============================================================*/
/* Table: 用户角色关系                                 */
/*==============================================================*/
drop table if exists dhlk_basic_user_role;
create table dhlk_basic_user_role 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   user_id              integer                        DEFAULT NULL COMMENT '用户id',
   role_id              integer                        DEFAULT NULL COMMENT '角色id',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

/*==============================================================*/
/* Table: 权限                               */
/*==============================================================*/
drop table if exists dhlk_basic_permissions;
create table dhlk_basic_permissions 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   menu_id              integer                        DEFAULT NULL COMMENT '菜单id',
   role_id              integer                        DEFAULT NULL COMMENT '角色id',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';

/*==============================================================*/
/* Table: API分类表                                   */
/*==============================================================*/
drop table if exists dhlk_api_classify;
create table dhlk_api_classify 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   class_name           varchar(50)                    DEFAULT NULL UNIQUE COMMENT '分类名称',
   parent_id            integer                        DEFAULT NULL COMMENT '父节点',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API分类表';

/*==============================================================*/
/* Table: 元数据表                                */
/*==============================================================*/
drop table if exists dhlk_basic_meta_table;
create table dhlk_basic_meta_table
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   table_enname         varchar(50)                    DEFAULT NULL COMMENT '元数据表编码',
   table_cnname         varchar(50)                    DEFAULT NULL COMMENT '元数据表名称',
   create_time          timestamp                      DEFAULT NOW() COMMENT '创建时间',
   status               integer                        DEFAULT '0' COMMENT '状态 0正常 2删除',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据表';

/*==============================================================*/
/* Table: 角色                                      */
/*==============================================================*/
drop table if exists dhlk_basic_role;
create table dhlk_basic_role 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(20)                    DEFAULT NULL UNIQUE COMMENT '名称',
   note                 varchar(200)                   DEFAULT NULL COMMENT '备注',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';
 /*默认超级管理员的角色id为1*/
INSERT INTO `dhlk_basic_role` VALUES ('1', '超级管理员', '拥有最大权限');

/*==============================================================*/
/* Table: 系统菜单                                      */
/*==============================================================*/
drop table if exists dhlk_basic_menu;
create table dhlk_basic_menu 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   code                 varchar(10)                    DEFAULT NULL COMMENT '编码',
   name                 varchar(50)                    DEFAULT NULL COMMENT '名称',
   url                  varchar(100)                   DEFAULT NULL COMMENT '请求连接',
   perms                varchar(500)                   DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
   parent_id             int(11)                        DEFAULT NULL COMMENT '父节点',
   type                 integer                        DEFAULT '0'  COMMENT '类型   0菜单   1按钮',
   status               integer                        DEFAULT '0'  COMMENT '状态  0正常 1禁用 2删除',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单';
/*==============================================================*/
/* Table: API接口                                         */
/*==============================================================*/
drop table if exists dhlk_api_list;
create table dhlk_api_list 
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   version              varchar(10)                    DEFAULT NULL COMMENT '版本',
   title                varchar(200)                   DEFAULT NULL COMMENT '接口名称',
   content              varchar(500)                   DEFAULT NULL COMMENT '接口说明',
   create_time          timestamp                      DEFAULT NOW() COMMENT '创建时间',
   status               integer                        DEFAULT '0' COMMENT '状态  0正常 1禁用',
   classify_id          integer                        DEFAULT NULL COMMENT '接口分类',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API接口';

/*==============================================================*/
/* Table: 元数据表列                           */
/*==============================================================*/
drop table if exists dhlk_basic_meta_table_column;
create table dhlk_basic_meta_table_column
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   column_enname        varchar(20)                    DEFAULT NULL COMMENT '列编码',
   column_cnname        varchar(20)                    DEFAULT NULL COMMENT '列名',
   data_type            varchar(10)                    DEFAULT NULL COMMENT '数据类型',
   length               integer                        DEFAULT NULL COMMENT '数据长度',
   table_id             integer                        DEFAULT NULL COMMENT '元数据表',
   status               integer                        DEFAULT '0' COMMENT '状态  0正常 2删除',
   primary key  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据表列';

/*==============================================================*/
/* Table: 设备属性集合                           */
/*==============================================================*/
DROP TABLE IF EXISTS dhlk_basic_devices_attr_set;
CREATE TABLE dhlk_basic_devices_attr_set
(
   id                   INTEGER                    NOT NULL AUTO_INCREMENT,
   `name`               VARCHAR(100)               DEFAULT NULL COMMENT '名称',
   `describe`           VARCHAR(200)               DEFAULT NULL COMMENT '描述',
   factory_id           INTEGER                    DEFAULT NULL COMMENT '工厂',
   attr_set_id          VARCHAR(100)               DEFAULT NULL COMMENT '属性ID',
   PRIMARY KEY  (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='设备属性集合';

/*==============================================================*/
/* Table: 设备属性明细                           */
/*==============================================================*/
DROP TABLE IF EXISTS dhlk_basic_devices_attr_detail;
CREATE TABLE dhlk_basic_devices_attr_detail
(
   id                    INTEGER                    NOT NULL AUTO_INCREMENT,
   attr                  VARCHAR(100)               DEFAULT NULL COMMENT '属性名称',
   data_type             VARCHAR(20)                DEFAULT NULL COMMENT '数据类型',
   data_length           INTEGER                    DEFAULT NULL COMMENT '数据长度',
   unit                  VARCHAR(100)               DEFAULT NULL COMMENT '单位',
   attr_set_id           INTEGER                    DEFAULT NULL COMMENT '属性集合',
   PRIMARY KEY  (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='设备属性明细';

/*==============================================================*/
/* Table: 设备分类                           */
/*==============================================================*/
DROP TABLE IF EXISTS dhlk_basic_devices_classify;
CREATE TABLE dhlk_basic_devices_classify
(
   id                   VARCHAR(100)                  NOT NULL,
   classify_name        VARCHAR(100)                  DEFAULT NULL COMMENT '分类名称',
   describe             VARCHAR(200)                  DEFAULT NULL COMMENT '描述',
   classify_id          VARCHAR(200)                  DEFAULT NULL COMMENT '类型',
   factory_id           INTEGER                       DEFAULT NULL COMMENT '工厂id',
   type_id              INTEGER                       DEFAULT NULL COMMENT '设备类型分类id',
   PRIMARY KEY  (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='设备类型管理';

/*==============================================================*/
/* Table: 设备类型属性明细                           */
/*==============================================================*/
DROP TABLE IF EXISTS dhlk_basic_devices_classify_detail;
CREATE TABLE dhlk_basic_devices_classify_detail
(
   id                   INTEGER                       NOT NULL AUTO_INCREMENT,
   attr_set_id          INTEGER                       DEFAULT NULL COMMENT '属性集合',
   attr_sub_name        VARCHAR(100)                  DEFAULT NULL COMMENT '属性简称',
   attr_detail_id       INTEGER                       DEFAULT NULL COMMENT '属性明细',
   devices_classify_id  VARCHAR(100)                  DEFAULT NULL COMMENT '设备分类',
   PRIMARY KEY  (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='设备类型属性明细';
/*==============================================================*/
/* Table: 公共附件表                          */
/*==============================================================*/
drop table if exists dhlk_basic_attachment;
create table dhlk_basic_attachment
(
    id          INTEGER       NOT NULL AUTO_INCREMENT,
    name        varchar(100),
    save_name   varchar(50),
    path        varchar(100),
    suffix      varchar(10),
    create_time timestamp,
    data_id     varchar(50),
    PRIMARY KEY  (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 AUTO_INCREMENT = 1 COMMENT '公共附件表';

/*==============================================================*/
/* Table: 网络设备类型                                     */
/*==============================================================*/
drop table if exists dhlk_dm_net_type;
create table dhlk_dm_net_type
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(100)                   DEFAULT NULL COMMENT '类型名称',
   status               integer                        DEFAULT '0' COMMENT '状态  0正常 2删除',
   PRIMARY KEY  (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 AUTO_INCREMENT = 1 COMMENT '网络设备类型';
/*==============================================================*/
/* Table: 设备类型分类                                     */
/*==============================================================*/
drop table if exists dhlk_dm_classify_type;
create table dhlk_dm_classify_type
(
   id                   integer                        NOT NULL AUTO_INCREMENT,
   name                 varchar(100)                   DEFAULT NULL COMMENT '分类名称',
   status               integer                        DEFAULT '0' COMMENT '状态  0正常 2删除',
   PRIMARY KEY  (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 AUTO_INCREMENT = 1 COMMENT '设备类型分类';
/*==============================================================*/
/* Function: 创建函数查出以逗号分开的字符串的第一个数                */
/*==============================================================*/
DELIMITER $$
DROP FUNCTION IF EXISTS `func_splitString`$$
CREATE FUNCTION `func_splitString`
( f_string VARCHAR(1000),f_delimiter VARCHAR(5),f_order INT)
RETURNS VARCHAR(255) CHARSET utf8
BEGIN
DECLARE str VARCHAR(255) DEFAULT '';
SET str = REVERSE(SUBSTRING_INDEX(REVERSE(SUBSTRING_INDEX(f_string,f_delimiter,f_order)),f_delimiter,1));
RETURN str;
END$$
DELIMITER ;


