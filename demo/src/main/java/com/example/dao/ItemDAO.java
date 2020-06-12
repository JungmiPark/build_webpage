package com.example.dao;

import java.util.HashMap;
import java.util.List;

import com.example.vo.ItemVO;

public interface ItemDAO {
	public int insertItem(ItemVO obj); //글쓰기
	public List<ItemVO> selectItem(HashMap<String, Object> map);
	public ItemVO selectItemOne(int no);
	public List<ItemVO> selectItemSearch(String txt);
	public int insertItemBatch(List<ItemVO> list);
	public int updateItemBatch(List<ItemVO> list);
	public List<ItemVO> selectItemWhere(int[] itemno);
	public int deleteItemBatch(int[] no);
}
