package IT6020003.process;

import java.util.*;
import java.sql.*;
import IT6020003.ConnectionPool;
import IT6020003.ConnectionPoolImpl;
import IT6020003.objects.*;

public class Section {
	// kết nối để làm việc vs csdl
	private Connection con;

	// bộ quản lý kết nối của riêng section
	private ConnectionPool cp;

	public Section() {
		// xác định bộ quản lý kết nối
		this.cp = new ConnectionPoolImpl();

		// Xin kết nối để làm việc
		try {
			this.con = this.cp.getConnection("Section");

			// kiểm tra chế độ thực thi của kết nối
			if (this.con.getAutoCommit()) {
				this.con.setAutoCommit(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<SectionObject> getSectionObjects(SectionObject similar, byte total) {
		
		ArrayList<SectionObject> items = new ArrayList<>();
		SectionObject item;
		
		String sql = "SELECT * FROM tblsection ";
		sql += "";
		sql += "ORDER BY section_name ASC ";
		sql += "LIMIT ?";

		// Biên dịch
//		Statement sta = this.con.createStatement(sql);
//		sta.executeQuery(sql);
//		CallableStatement call = this.con.prepareCall(sql);
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql);
			// truyền giá trị cho tham số, tổng số bản ghi
			pre.setByte(1, total);

			ResultSet rs = pre.executeQuery(); // lấy về tập kết quả
			if (rs != null) {
				while (rs.next()) {
					item = new SectionObject();
//					item.setSection_id(rs.getShort(1));
					item.setSection_id(rs.getShort("section_id"));
					item.setSection_name(rs.getString("section_name"));
					
					// đưa vào tập hợp
					items.add(item);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				this.con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return items;
	}
	
	public boolean addSection(SectionObject item) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tblsection(");
		sql.append("section_name, section_notes, section_created_date, ");
		sql.append("section_manager_id, section_enable, section_delete, ");
		sql.append("section_last_modified, section_created_author_id, ");
		sql.append("section_name_en, section_language) ");
		sql.append("VALUES(?,?,?,?,?,?,?,?,?,?)");
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getSection_name());
			pre.setString(2, item.getSection_notes());
			pre.setString(3, item.getSection_created_date());
			pre.setInt(4, item.getSection_manager_id());
			pre.setBoolean(5, item.isSection_enable());
			pre.setBoolean(6, item.isSection_delete());
			pre.setString(7, item.getSection_last_modified());
			pre.setInt(8, item.getSection_created_author_id());
			pre.setString(9, item.getSection_name_en());
			pre.setByte(10, item.getSection_language());
			
			int result = pre.executeUpdate();
			if (result == 0) {
				this.con.rollback();
				return false;
			}
			// ghi nhận thực thi sau cùng
			this.con.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return false;
	}
	
	// update
	public boolean updateSection(SectionObject item) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tblsection SET ");
		sql.append("section_name = ?, ");
		sql.append("section_notes = ?, ");
		sql.append("section_created_date = ?, ");
		sql.append("section_manager_id = ?, ");
		sql.append("section_enable = ?, ");
		sql.append("section_delete = ?, ");
		sql.append("section_last_modified = ?, ");
		sql.append("section_created_author_id = ?, ");
		sql.append("section_name_en = ?, ");
		sql.append("section_language = ? ");
		sql.append("WHERE section_id = ?");
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getSection_name());
			pre.setString(2, item.getSection_notes());
			pre.setString(3, item.getSection_created_date());
			pre.setInt(4, item.getSection_manager_id());
			pre.setBoolean(5, item.isSection_enable());
			pre.setBoolean(6, item.isSection_delete());
			pre.setString(7, item.getSection_last_modified());
			pre.setInt(8, item.getSection_created_author_id());
			pre.setString(9, item.getSection_name_en());
			pre.setByte(10, item.getSection_language());
			pre.setShort(11, item.getSection_id());
			
			int result = pre.executeUpdate();
	        if (result == 0) {
	            this.con.rollback();
	            return false;
	        }
			
	        this.con.commit();
	        return true;
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
		}
		
		return false;
	}
	
	// Delete
	public boolean deleteSectionById(short sectionId) {
	    String sql = "DELETE FROM tblsection WHERE section_id = ?";
	    
	    try {
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        pre.setShort(1, sectionId);
	        
	        int result = pre.executeUpdate();
	        if (result == 0) {
	            this.con.rollback();
	            return false;
	        }
	        
	        this.con.commit();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	        try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    
	    return false;
	}
	
	
	public static void main(String[] args) {
		// tạo đối tượng làm việc với Section
		Section s = new Section();
		
		// tạo đối tượng chuyên mục mới
		SectionObject nsec = new SectionObject();
		nsec.setSection_name("LT Java nâng cao");
		nsec.setSection_created_date("21/12/23");
		
		if(!s.addSection(nsec)) {
			System.out.println("--------Không thành công");
		}
		
		// Lấy danh sách đối tượng
		ArrayList<SectionObject> items = s.getSectionObjects(null, (byte)5);
		
		// In ra màn hình
		items.forEach(item -> {
			System.out.println(item);
		});
		
		// Chọn một phần tử để cập nhật
	    SectionObject selectedSection = items.get(0);
	    selectedSection.setSection_name("LT Java nâng cao - Cập nhật");
	    
	    // Cập nhật thông tin
	    if (s.updateSection(selectedSection)) {
	        System.out.println("Cập nhật thành công");
	    } else {
	        System.out.println("Cập nhật không thành công");
	    }
	    
	    // In ra màn hình
	    items.forEach(item -> {
			System.out.println(item);
		});
	    
	    
	}
}
