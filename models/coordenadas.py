from utils.db import db

class Coordenada(db.Model):
    __tablename__ = 'coordenadas'
    id = db.Column(db.Integer, primary_key=True)
    distrito_id = db.Column(db.String(6), db.ForeignKey('distritos.distritoid'))
    latitude = db.Column(db.Float)
    longitude = db.Column(db.Float)
    
    def __init__(self, distrito_id, latitude, longitude):
        self.distrito_id = distrito_id
        self.latitude = latitude
        self.longitude = longitude

    def serialize(self):
        return {
            'id': self.id,
            'distrito_id': self.distrito_id,
            'latitude': self.latitude,
            'longitude': self.longitude
        }

    def __repr__(self):
        return f'Coordenada ({self.latitude}, {self.longitude})'
