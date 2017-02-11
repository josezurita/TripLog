create table usuario(
	idUsuario serial primary key,
	usuario varchar(100) not null,
	contrasena varchar(100) not null,
	nombre varchar(150)  not null,
	email varchar(100),
	activo boolean not null
);

create table viaje(
	idViaje serial primary key,
	nombre varchar(100) not null,
	descripcion varchar(500),
	lugar varchar(100) not null,
	favorito boolean not null,
	activo boolean not null ,
	idUsuario integer  references usuario
);

create table historia(
	idHistoria serial primary key,
	nombre varchar(100) not null,
	descripcion varchar(500),
	imagen varchar(500),
	activo boolean not null ,
	idViaje integer references viaje
);

create table equipaje(
	idEquipaje serial primary key,
	item varchar(100) not null,
	listo boolean not null,
	activo boolean not null,
	idViaje integer references viaje
);

create table comentario(
	idComentario serial primary key,
	descripcion varchar(500) not null,
	activo boolean not null,
	idUsuario integer references usuario
);

create table recordatorio(
	idRecordatorio serial primary key,
	hora varchar(10) not null,
	dias varchar(5) not null,
	switch boolean not null,
	activo boolean not null
);