create table utilisateur
(
    id_user int
    auto_increment
        primary key,
    firstname varchar
    (50) null,
    lastname  varchar
    (50) null
)
    charset = utf8;

    create table gamebook
    (
        id_gamebook int
        auto_increment
        primary key,
    title       varchar
        (150) null,
    isbn        varchar
        (13)  null,
    resume      varchar
        (500) null,
    is_publish  tinyint
        (1)   null,
    id_user     int          not null,
    constraint isbn
        unique
        (isbn),
    constraint gamebook_ibfk_1
        foreign key
        (id_user) references utilisateur
        (id_user)
)
    charset = utf8;

        create index id_user
    on gamebook (id_user);

        create table page
        (
            id_page int
            auto_increment
        primary key,
    text        varchar
            (50) null,
    numero      int         null,
    id_gamebook int         not null,
    constraint page_ibfk_1
        foreign key
            (id_gamebook) references gamebook
            (id_gamebook)
)
    charset = utf8;

            create table choix
            (
                id_page int not null,
                id_page_1 int not null,
                text varchar(100) null,
                primary key (id_page, id_page_1),
                constraint choix_ibfk_1
        foreign key (id_page) references page (id_page),
                constraint choix_ibfk_2
        foreign key (id_page_1) references page (id_page)
            );

            create index id_page_1
    on choix (id_page_1);

            create index id_gamebook
    on page (id_gamebook);
