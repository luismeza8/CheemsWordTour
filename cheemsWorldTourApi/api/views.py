from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import *
from .models import *

# Create your views here.
@api_view(['GET'])
def obtener_todos(request):
    registros = Registro.objects.all()
    serializers = RegistroSerializer(registros, many=True)
    return Response(serializers.data)


@api_view(['GET'])
def obtener_registro(request, pk):
    registro = Registro.objects.get(pk=pk)
    serializer = RegistroSerializer(instance=registro)
    return Response(serializer.data)


@api_view(['POST'])
def agregar_registro(request):
    serializer = RegistroSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)

