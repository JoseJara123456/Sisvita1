from utils.db import db

class Diagnostico(db.Model):
    __tablename__ = 'diagnostico'
    id_diagnostico = db.Column(db.Integer, primary_key=True)
    usuarioid = db.Column(db.Integer)
    test_id = db.Column(db.Integer)
    id_tratamiento = db.Column(db.Integer)
    nivelansiedad = db.Column(db.String(50))
    fundamentacion = db.Column(db.Text)
    observacion = db.Column(db.Text)
    fecha_diagnostico = db.Column(db.Date)

    def __init__(self, usuarioid, test_id, id_tratamiento, nivelansiedad, fundamentacion, observacion, fecha_diagnostico):
        self.usuarioid = usuarioid
        self.test_id = test_id
        self.id_tratamiento = id_tratamiento
        self.nivelansiedad = nivelansiedad
        self.fundamentacion = fundamentacion
        self.observacion = observacion
        self.fecha_diagnostico = fecha_diagnostico

    def serialize(self):
        return {
            'id_diagnostico': self.id_diagnostico,
            'usuarioid': self.usuarioid,
            'test_id': self.test_id,
            'id_tratamiento': self.id_tratamiento,
            'nivelansiedad': self.nivelansiedad,
            'fundamentacion': self.fundamentacion,
            'observacion': self.observacion,
            'fecha_diagnostico': self.fecha_diagnostico.isoformat() if self.fecha_diagnostico else None,
        }