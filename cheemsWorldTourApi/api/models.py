from django.db import models

# Create your models here.
class Registro(models.Model):
    responsable = models.CharField(max_length=100, default='')
    motivo = models.CharField(max_length=500, default='')
    latitud = models.FloatField()
    longitud = models.FloatField()
