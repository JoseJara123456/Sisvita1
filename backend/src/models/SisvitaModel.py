from database.db import get_connection
from .entities.especialistas import especialistas

class SisvitaModel():

    @classmethod
    def get_especialistas(self):
        try:
            connection = get_connection()
            especialista = []

            with connection.cursor() as cursor:
                cursor.execute("SELECT usuarioid, especialidad FROM public.especialistas")
                resultset = cursor.fetchall()

                for row in resultset:
                    especialistas = especialistas(row[0], row[1])
                    especialista.append(especialistas.to_JSON())

            connection.close()
            return especialista
        except Exception as ex:
            raise Exception(ex)
