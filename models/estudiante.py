from utils.db import db

class Estudiante(db.Model):
    __tablename__ = 'estudiante'
    usuarioid = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(100), nullable=False, unique=True)
    password = db.Column(db.String(200), nullable=False)
    rol = db.Column(db.String(20), nullable=False)
    ubigeoid = db.Column(db.Integer, db.ForeignKey('ubigeo.ubigeoid'))

    def __init__(self, nombre, email, password, rol,ubigeoid):
        self.nombre = nombre
        self.email = email
        self.password = password
        self.rol = rol
        self.ubigeoid = ubigeoid
        
    def serialize(self):
        return {
            'usuarioid': self.usuarioid,
            'nombre': self.nombre,
            'email': self.email,
            'password': self.password,
            'rol': self.rol,
            'ubigeoid': self.ubigeoid
        }
