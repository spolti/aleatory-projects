/*
    Copyright (C) 2012  Filippe Costa Spolti

    This is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.ctbc.spolti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/*
 * @Author - Filippe Costa Spolti, filippespolti@gmail.com
 * 12/11/2012
 */


public class EncryptSystemPropertiesService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger
			.getLogger(EncryptSystemPropertiesService.class);
	
	private Properties props = new Properties();
	private InputStream str = this.getClass().getClassLoader()
			.getResourceAsStream("log4j.properties");
	

	public EncryptSystemPropertiesService() throws IOException {
		loadSystemProperties();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		props.load(str);
		PrintWriter pw = resp.getWriter();
		
		try {
			logger.info("Encripitando senha...");
			String pwd = req.getParameter("password");
			pw.println("<html>");
			pw.println("  <body>");
			pw.println("    <p> Encrypted password: " + encode(pwd) + " </p>");
			pw.println("  </body>");
			pw.println("</html>");
			logger.info("Senha Encriptada, monstrando no browser.");
		} catch (Exception e) {
			pw.println("<html>");
			pw.println("  <body>");
			pw.println("    <p> Erro ao encriptar senha: " + e + " </p>");
			pw.println("  </body>");
			pw.println("</html>");
		}

	}

	private void loadSystemProperties() throws IOException {
		props.load(str);
		File file = null;

		try {
			logger.info("Carregando arquivo encrypted-properties.properties.");
			Properties properties = new Properties();
			InputStream stream = this.getClass().getClassLoader()
					.getResourceAsStream("encrypted-properties.properties");
			properties.load(stream);
			logger.info("encrypted-properties.properties caregado com sucesso.");
			String value = properties.getProperty("ssl.keyStorePassword");
			// Set the decrypted value in the System properties
			String decodedValue = decode(value);
			System.setProperty("javax.net.ssl.keyStorePassword", decodedValue);
			logger.info("SSL Keystore Password setado com sucesso.");

		} catch (Exception e) {
			if (file == null) {
				logger.error("Ocorreu algum erro durante o carregamento do certificado: "
						+ e);
			}
		}
	}

	private static String decode(String secret) throws Exception {
		byte[] kbytes = "spoltis way".getBytes();
		SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
		BigInteger n = new BigInteger(secret, 16);
		byte[] encoding = n.toByteArray();
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decode = cipher.doFinal(encoding);
		return new String(decode);
	}

	private static String encode(String secret) throws Exception {
		byte[] kbytes = "spoltis way".getBytes();
		SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encoding = cipher.doFinal(secret.getBytes());
		BigInteger n = new BigInteger(encoding);
		return n.toString(16);
	}
	
}