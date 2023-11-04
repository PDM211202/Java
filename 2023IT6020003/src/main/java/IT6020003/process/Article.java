package IT6020003.process;

import java.util.*;
import java.sql.*;
import IT6020003.ConnectionPool;
import IT6020003.ConnectionPoolImpl;
import IT6020003.objects.*;

public class Article {
	// kết nối để làm việc vs csdl
	private Connection con;

	// bộ quản lý kết nối của riêng section
	private ConnectionPool cp;

	public Article() {
		// xác định bộ quản lý kết nối
		this.cp = new ConnectionPoolImpl();

		// Xin kết nối để làm việc
		try {
			this.con = this.cp.getConnection("Article");

			// kiểm tra chế độ thực thi của kết nối
			if (this.con.getAutoCommit()) {
				this.con.setAutoCommit(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Get all ArticleObjects
	public ArrayList<ArticleObject> getAllArticleObjects(ArticleObject similar, byte total) {

		ArrayList<ArticleObject> items = new ArrayList<>();
		ArticleObject item;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblarticle");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());

			ResultSet rs = pre.executeQuery(); // lấy về tập kết quả
			if (rs != null) {
				while (rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getInt("article_id"));
					item.setArticle_title(rs.getString("article_title"));

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

	// Get ArticleObjects
	public ArrayList<ArticleObject> getArticleObjects(ArticleObject similar, byte total) {

		ArrayList<ArticleObject> items = new ArrayList<>();
		ArticleObject item;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblarticle ");
		sql.append("ORDER BY article_title ASC ");
		sql.append("LIMIT ?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			// truyền giá trị cho tham số, tổng số bản ghi
			pre.setByte(1, total);

			ResultSet rs = pre.executeQuery(); // lấy về tập kết quả
			if (rs != null) {
				while (rs.next()) {
					item = new ArticleObject();
					item.setArticle_id(rs.getInt("article_id"));
					item.setArticle_title(rs.getString("article_title"));

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

	// Add Article
	public boolean addArticle(ArticleObject item) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tblarticle(");
		sql.append("article_title, article_summary, article_content, ");
		sql.append("article_created_date, article_last_modified, article_image, ");
		sql.append("article_category_id, article_section_id, article_visited, ");
		sql.append("article_author_name, article_enable, article_url_link, ");
		sql.append("article_tag, article_title_en, article_summary_en, ");
		sql.append("article_content_en, article_tag_en, article_fee, ");
		sql.append("article_isfee, article_delete, article_deleted_date, ");
		sql.append("article_restored_date, article_modified_author_name, article_author_permission, ");
		sql.append("article_source, article_language, article_focus, ");
		sql.append("article_type, article_forhome) ");
		sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getArticle_title());
			pre.setString(2, item.getArticle_summary());
			pre.setString(3, item.getArticle_content());
			pre.setString(4, item.getArticle_created_date());
			pre.setString(5, item.getArticle_last_modified());
			pre.setString(6, item.getArticle_image());
			pre.setShort(7, item.getArticle_category_id());
			pre.setShort(8, item.getArticle_section_id());
			pre.setShort(9, item.getArticle_visited());
			pre.setString(10, item.getArticle_author_name());
			pre.setShort(11, item.getArticle_enable());
			pre.setString(12, item.getArticle_url_link());
			pre.setString(13, item.getArticle_tag());
			pre.setString(14, item.getArticle_title_en());
			pre.setString(15, item.getArticle_summary_en());
			pre.setString(16, item.getArticle_content_en());
			pre.setString(17, item.getArticle_tag_en());
			pre.setInt(18, item.getArticle_fee());
			pre.setShort(19, item.getArticle_isfee());
			pre.setShort(20, item.getArticle_delete());
			pre.setString(21, item.getArticle_deleted_date());
			pre.setString(22, item.getArticle_restored_date());
			pre.setString(23, item.getArticle_modified_author_name());
			pre.setShort(24, item.getArticle_author_permission());
			pre.setString(25, item.getArticle_source());
			pre.setShort(26, item.getArticle_language());
			pre.setShort(27, item.getArticle_focus());
			pre.setShort(28, item.getArticle_type());
			pre.setShort(29, item.getArticle_forhome());

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

	// Update Article by ID
	public boolean updateArticle(ArticleObject item) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tblarticle SET ");
		sql.append("article_title = ?, ");
		sql.append("article_summary = ?, ");
		sql.append("article_content = ?, ");
		sql.append("article_created_date = ?, ");
		sql.append("article_last_modified = ?, ");
		sql.append("article_image = ?, ");
		sql.append("article_category_id = ?, ");
		sql.append("article_section_id = ?, ");
		sql.append("article_visited = ?, ");
		sql.append("article_author_name = ?, ");
		sql.append("article_enable,  = ?, ");
		sql.append("article_url_link,  = ?, ");
		sql.append("article_tag = ?, ");
		sql.append("article_title_en = ?, ");
		sql.append("article_summary_en = ?, ");
		sql.append("article_content_en = ?, ");
		sql.append("article_tag_en = ?, ");
		sql.append("article_fee = ?, ");
		sql.append("article_isfee = ?, ");
		sql.append("article_delete = ?, ");
		sql.append("article_deleted_date = ?, ");
		sql.append("article_restored_date = ?, ");
		sql.append("article_modified_author_name = ?, ");
		sql.append("article_author_permission = ?, ");
		sql.append("article_source = ?, ");
		sql.append("article_language = ?, ");
		sql.append("article_focus = ?, ");
		sql.append("article_type = ?, ");
		sql.append("article_forhome = ? ");
		sql.append("WHERE article_id = ?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getArticle_title());
			pre.setString(2, item.getArticle_summary());
			pre.setString(3, item.getArticle_content());
			pre.setString(4, item.getArticle_created_date());
			pre.setString(5, item.getArticle_last_modified());
			pre.setString(6, item.getArticle_image());
			pre.setShort(7, item.getArticle_category_id());
			pre.setShort(8, item.getArticle_section_id());
			pre.setShort(9, item.getArticle_visited());
			pre.setString(10, item.getArticle_author_name());
			pre.setShort(11, item.getArticle_enable());
			pre.setString(12, item.getArticle_url_link());
			pre.setString(13, item.getArticle_tag());
			pre.setString(14, item.getArticle_title_en());
			pre.setString(15, item.getArticle_summary_en());
			pre.setString(16, item.getArticle_content_en());
			pre.setString(17, item.getArticle_tag_en());
			pre.setInt(18, item.getArticle_fee());
			pre.setShort(19, item.getArticle_isfee());
			pre.setShort(20, item.getArticle_delete());
			pre.setString(21, item.getArticle_deleted_date());
			pre.setString(22, item.getArticle_restored_date());
			pre.setString(23, item.getArticle_modified_author_name());
			pre.setShort(24, item.getArticle_author_permission());
			pre.setString(25, item.getArticle_source());
			pre.setShort(26, item.getArticle_language());
			pre.setShort(27, item.getArticle_focus());
			pre.setShort(28, item.getArticle_type());
			pre.setShort(29, item.getArticle_forhome());
			pre.setInt(30, item.getArticle_id());

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
	public boolean deleteArticleById(short articleId) {
		String sql = "DELETE FROM tblarticle WHERE article_id = ?";

		try {
			PreparedStatement pre = this.con.prepareStatement(sql);
			pre.setInt(1, articleId);

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
		// tạo đối tượng làm việc với Article
		Article a = new Article();

		// tạo đối tượng chuyên mục mới
		ArticleObject nart = new ArticleObject();
		nart.setArticle_title("Article_title - new");
		nart.setArticle_author_name("new");

		if (!a.addArticle(nart)) {
			System.out.println("--------Không thành công");
		}

		ArrayList<ArticleObject> items = a.getArticleObjects(null, (byte) 5);

		// In ra màn hình
		items.forEach(item -> {
			System.out.println(item);
		});
	}
}
