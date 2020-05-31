
public class Buffer {
	byte[] bytes;
	int start;
	int end;
	
	
	public Buffer(int bytes) {
		this.bytes = new byte[bytes];
		this.setStart(0);
		this.setEnd(bytes-1);
	}
	
	private void setEnd(int unosBytes) {
		this.end = this.start + unosBytes;
	}

	public void setStart(int start) {
		this.start = start;
	}



	public byte[] getBytes() {
		return bytes;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	
	
}
