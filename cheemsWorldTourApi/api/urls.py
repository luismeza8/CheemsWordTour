from django.urls import path

from . import views

urlpatterns = [
    path('obtener_todos', views.obtener_todos),
    path('obtener_registro/<int:pk>', views.obtener_registro),
    path('agregar_registro', views.agregar_registro)
]
