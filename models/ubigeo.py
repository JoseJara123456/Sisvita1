from utils.db import db

class Ubigeo(db.Model):
    __tablename__ = 'ubigeo'
    ubigeoid = db.Column(db.Integer, primary_key=True)
    departamento = db.Column(db.String(100), nullable=False)
    provincia = db.Column(db.String(100), nullable=False)
    distrito = db.Column(db.String(100), nullable=False)
    latitud = db.Column(db.Numeric, nullable=True)
    longitud = db.Column(db.Numeric, nullable=True)

    def __init__(self, departamento, provincia, distrito, latitud=None, longitud=None):
        self.departamento = departamento
        self.provincia = provincia
        self.distrito = distrito
        self.latitud = latitud
        self.longitud = longitud

    def serialize(self):
        return {
            'ubigeoid': self.ubigeoid,
            'departamento': self.departamento,
            'provincia': self.provincia,
            'distrito': self.distrito,
            'latitud': self.latitud,
            'longitud': self.longitud
        }
