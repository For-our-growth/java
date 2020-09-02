public void complete(HashMap<String, Object> map) {
	openMap(map);
}
	
// Map의 key, value 출력
public void openMap(Map<String, Object> map) {
	Iterator<String> keyIterator = map.keySet().iterator();
		
	while(keyIterator.hasNext()) {
		String key = keyIterator.next();
		Object value = map.get(key);
		
		// value가 List type일 때
		if(value instanceof List) {
			System.out.println(key + " : (List Open)");
			openList(value);
			continue;
		}
		
		// value가 Map type일 때
		if(value instanceof Map) {
			System.out.println(key + " : (Map Open)");
			openMap((Map) value);
			continue;
		}
			
		System.out.println(key + " : " + value);
	}
}
	
// List 속 Map 한 개씩 처리
public void openList(Object value) {
	List<Map<String, Object>> listData = (List) value;
	for(Map<String, Object> mapValue : listData) {
		openMap(mapValue);
	}
}