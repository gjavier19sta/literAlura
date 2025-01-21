package aluraLatam.literAlura.service;

public interface IConvertirData {
    <T> T obtenerDatos(String Json, Class<T> clase);

}
