import java.io.*;

import java.util.zip.*;

public class ZipFileList {
	public static void main(String[] args) {
		extZipFileList("E:/software/lumaqq_2004-win32_x86_with_jre.zip ", "E:/software/ ");

	}

	private static void extZipFileList(String zipFileName, String extPlace) {
		try {

			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));

			ZipEntry entry = null;

			while ((entry = in.getNextEntry()) != null) {

				String entryName = entry.getName();

				if (entry.isDirectory()) {
					File file = new File(extPlace + entryName);
					file.mkdirs();
					System.out.println("创建文件夹: " + entryName);
				} else {

					FileOutputStream os = new FileOutputStream(extPlace + entryName);

					//   Transfer   bytes   from   the   ZIP   file   to   the   output   file 
					byte[] buf = new byte[1024];

					int len;
					while ((len = in.read(buf)) > 0) {
						os.write(buf, 0, len);
					}
					os.close();
					in.closeEntry();

				}
			}

		} catch (IOException e) {
		}
		System.out.println("解压文件成功 ");
	}
}
