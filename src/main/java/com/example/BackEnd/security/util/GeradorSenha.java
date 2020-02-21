
package com.example.BackEnd.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {
		public static void main(String[] args) {
			BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
			System.out.println(encoder.encode("2102"));
		}
		//$2a$10$7a9Rejv7C/im9O/O.dV/mOdq7ERvVFsFepnGjsu.Jd15yDdXGxBKW
}
