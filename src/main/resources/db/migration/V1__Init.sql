create table if not exists task_management_system.users(
    id int not null auto_increment,
    name varchar(20) not null,
    surname varchar(20) not null,
    birthDate datetime not null,
    email varchar(60) not null,
    password varchar(200) not null,
    primary key(id),
    unique index idx_users_id_unique(id asc),
    unique index idx_users_email_unique(email asc));

drop table if exists task_management_system.tasks;
create table if not exists task_management_system.tasks(
    id int not null auto_increment,
    title varchar(60) not null,
    description varchar(2000) not null,
    status varchar(30) not null,
    priority varchar(30) not null,
    creation_date datetime not null,
    author_id int not null,
    primary key(id),
    unique index idx_tasks_id_unique(id ASC),
    constraint fk_tasks_author_id_users_id
    foreign key (author_id)
    references task_management_system.users(id)
    on delete cascade
    on update cascade);

drop table if exists task_management_system.tasks_users;
create table if not exists task_management_system.tasks_users(
    task_id int not null,
    user_id int not null,
    primary key(task_id,user_id),
    constraint fk_tasks_users_task_id_tasks_id
    foreign key(task_id)
    references task_management_system.tasks (id),
    constraint fk_tasks_users_user_id_users_id
    foreign key(user_id)
    references task_management_system.users (id)
    on delete cascade
    on update cascade);

drop table if exists task_management_system.comments;
create table if not exists task_management_system.comments(
    id int not null,
    comment_text varchar(500) not null,
    date datetime not null,
    user_id int not null,
    task_id int not null,
    primary key(id),
    unique index idx_comments_id_unique (id ASC),
    constraint fk_comments_user_id_users_id
    foreign key(user_id)
    references task_management_system.users (id),
    constraint fk_comments_task_id_tasks_id
    foreign key (task_id)
    references task_management_system.tasks (id)
    on delete cascade
    on update cascade);

    create table if not exists  task_management_system.refresh_tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(300) NOT NULL,
    username VARCHAR(64) NOT NULL,
    CONSTRAINT FK_refresh_tokens_username_users_email
    FOREIGN KEY (username)
    REFERENCES task_management_system.users (email)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
