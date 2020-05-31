import java.util.function.Consumer;

import exceptions.LecturaInvalidaException;

public class FileSystemPosta {
	LowLevelFileSystem lowLevelFileSystem;
	
	public FileSystemPosta(LowLevelFileSystem lowLevelFileSystem) {
		this.lowLevelFileSystem = lowLevelFileSystem;
	}
	
	public int abrirUnArchivo(String file){
		return lowLevelFileSystem.openFile(file);
	}
	
	public void cerrarUnArchivo(int fd){
		lowLevelFileSystem.closeFile(fd);
	}

	public void leerUnArchivoSincronicamente(String file, int bytes) {
		int fd = lowLevelFileSystem.openFile(file);
		Buffer buffer = new Buffer(bytes);
		int resultado = lowLevelFileSystem.syncReadFile(fd, buffer.getBytes(), buffer.getStart(), buffer.getEnd());
		this.validarLectura(resultado);
		lowLevelFileSystem.closeFile(fd);
	}
	
	public void leerUnArchivoAsincronicamente(String file, int bytes, Consumer<Integer> callback) {
		int fd = lowLevelFileSystem.openFile(file);
		Buffer buffer = new Buffer(bytes);
		lowLevelFileSystem.asyncReadFile(fd, buffer.getBytes(), buffer.getStart(), buffer.getEnd(), callback);
		lowLevelFileSystem.closeFile(fd);
	}
	
	public void escribirUnArchivoSincronicamente(String file, int bytes) {
		int fd = lowLevelFileSystem.openFile(file);
		Buffer buffer = new Buffer(bytes);
		lowLevelFileSystem.syncWriteFile(fd, buffer.getBytes(), buffer.getStart(), buffer.getEnd());
		lowLevelFileSystem.closeFile(fd);
	}

	private void validarLectura(int resultado) {
		if(resultado < 0)
			throw new LecturaInvalidaException("La lectura no se pudo realizar");
	}
	
	
}
