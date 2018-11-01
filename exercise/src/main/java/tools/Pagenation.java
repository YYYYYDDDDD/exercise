package tools;

public class Pagenation {
	public static long offset( int currentPage, int pageSize) {
		if( currentPage < 0 || pageSize < 1 ) {
			throw new RuntimeException("Parameters out of index " + currentPage + "," + pageSize );
		}
		return (currentPage - 1) * pageSize;
	}
	@Deprecated
	public static long limit( int currentPage, int pageSize) {
		return pageSize;
	}
}
