import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

import static org.mockito.Mockito.*;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class FileSystemTest {
	FileSystemPosta fileSystem;
	LowLevelFileSystem lowLevelFileSystem;
	Buffer buffer;
	
	@Before
	public void init() {
		lowLevelFileSystem = mock(LowLevelFileSystem.class);
		fileSystem = new FileSystemPosta(lowLevelFileSystem);
	}
	
	@Test
	public void abrirUnArchivo() {
		fileSystem.abrirUnArchivo("archivo.txt");
		verify(lowLevelFileSystem).openFile("archivo.txt");
	}
	
	@Test
	public void cerrarUnArchivo() {
		fileSystem.cerrarUnArchivo(1);
		verify(lowLevelFileSystem).closeFile(1);
	}
	
	@Test
	public void leerUnArchivoSincronicamente3Veces() {
		when(fileSystem.abrirUnArchivo("archivo.txt")).thenReturn(2);
		fileSystem.leerUnArchivoSincronicamente("archivo.txt", 4);
		fileSystem.leerUnArchivoSincronicamente("archivo.txt", 1);
		fileSystem.leerUnArchivoSincronicamente("archivo.txt", 5);
		
		verify(lowLevelFileSystem).syncReadFile(2, new byte[4], 0, 3);
		verify(lowLevelFileSystem).syncReadFile(2, new byte[1], 0, 0);
		verify(lowLevelFileSystem).syncReadFile(2, new byte[5], 0, 4);
	}
	
	@Test
	public void escribirUnArchivoSincronicamente3Veces() {
		when(fileSystem.abrirUnArchivo("archivo.txt")).thenReturn(2);
		fileSystem.escribirUnArchivoSincronicamente("archivo.txt", 4);
		fileSystem.escribirUnArchivoSincronicamente("archivo.txt", 1);
		fileSystem.escribirUnArchivoSincronicamente("archivo.txt", 5);
		
		verify(lowLevelFileSystem).syncWriteFile(2, new byte[4], 0, 3);
		verify(lowLevelFileSystem).syncWriteFile(2, new byte[1], 0, 0);
		verify(lowLevelFileSystem).syncWriteFile(2, new byte[5], 0, 4);
	}
	
	@Test
	public void leerUnArchivoAsincronicamente() {
		Consumer <Integer> callback = (Integer a) -> {};
		when(fileSystem.abrirUnArchivo("archivo.txt")).thenReturn(2);
		fileSystem.leerUnArchivoAsincronicamente("archivo.txt", 4, callback);
		
		verify(lowLevelFileSystem).asyncReadFile(2, new byte[4], 0, 3, callback);
	}
}
