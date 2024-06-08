from utils.db import db

class Usuarios(db.Model):
    __tablename__ = 'usuarios'
    usuarioid = db.Column(db.Integer, primary_key=True, autoincrement=False)
    nombre = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(100), unique=True, nullable=False)
    password = db.Column(db.String(100), nullable=False)
    rol = db.Column(db.String(20), nullable=False)

    def __init__(self, usuarioid, nombre, email, password, rol):
        self.usuarioid = usuarioid
        self.nombre = nombre
        self.email = email
        self.password = password
        self.rol = rol
        
    def serialize(self):
        return {
            'usuarioid': self.UsuarioID,
            'nombre': self.nombre,
            'email': self.email,
            'password': self.password,
            'rol': self.rol,
        }
