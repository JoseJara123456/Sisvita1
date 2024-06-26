from utils.db import db

class testRealizados(db.Model):
    __tablename__ = 'test_realizados'
    test_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    fecha_test = db.Column(db.DateTime)
    tipotest_id = db.Column(db.Integer)
    usuarioid = db.Column(db.Integer)
    puntaje = db.Column(db.Integer)
    nivel_ansiedad = db.Column(db.String(50))

    def __init__(self, fecha_test, tipotest_id, usuarioid, puntaje, nivel_ansiedad):
        self.fecha_test = fecha_test
        self.tipotest_id = tipotest_id
        self.usuarioid = usuarioid
        self.puntaje = puntaje
        self.nivel_ansiedad=nivel_ansiedad

    def serialize(self):
        return {
            'test_id': self.test_id,
            'fecha_test': self.fecha_test.strftime('%Y-%m-%d') if self.fecha_test else None,
            'tipotest_id': self.tipotest_id,
            'usuarioid': self.usuarioid,
            'puntaje': self.puntaje,
            'nivel_ansiedad':self.nivel_ansiedad
        }
