CREATE table user(
  id int auto_increment,
  open_id varchar(70),
  level int,
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  head_imgurl VARCHAR(255),
  nick_name varchar(100),
  sex varchar(5),
  country varchar(50),
  province varchar(50),
  city varchar(50),
  PRIMARY KEY(id),
  UNIQUE KEY `user_open_id_key` (`open_id`)
);

CREATE table record(
  id int auto_increment,
  red_id varchar(255),
  red_address varchar(255),
  open_id VARCHAR(255),
   PRIMARY KEY(id)
);

