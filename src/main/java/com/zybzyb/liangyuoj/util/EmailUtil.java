package com.zybzyb.liangyuoj.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

import java.sql.Timestamp;

@Component
public class EmailUtil {

    // 注册账号
    private static String signUp(String email, String code) {
        String htmlContent = """
            <!DOCTYPE html>
            <html>
              <head>
                <title>验证码邮件</title>
              </head>
              <body style="font-family: Arial; color: #333333">
                <div
                  style="
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #ffffff;
                    padding: 20px;
                  "
                >
                  <h2 style="color: #b70031">您好，<span style="font-size: 16px;">%s</span></h2>
                  <p>
                    您于 %s 尝试验证此邮箱地址，验证码为：
                    <span style="font-size: 24px; color: #b70031; font-weight: 700">%s</span>
                  </p>
                  <p>请输入此验证码完成操作，验证码15分钟内有效。</p>
                  <p style="font-size: 14px; color: #888888; margin-top: 20px">
                    如果您没有进行相关操作，请忽略此邮件。<br />
                    感谢您的使用!
                  </p>
                </div>
              </body>
            </html>
                    """;
        return String.format(htmlContent, email, getNowTime(), code);
    }


    private static String getNowTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp
            .toString()
            .substring(0, timestamp.toString()
                .indexOf("."));
    }

    public static void sendMail(String sender, String email, String verificationCode, JavaMailSender jms)
        throws Exception {

        // 建立邮件消息
        MimeMessage mainMessage = jms.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mainMessage, true);

        // 发送者
        helper.setFrom(sender);

        // 接收者
        helper.setTo(email);

        // 发送的标题
        helper.setSubject("「LYOJ」验证码");
        String msg;

        msg = signUp(email, verificationCode);
        helper.setText(msg, true);

        // 发送邮件
        jms.send(mainMessage);

    }

    public static int getRandNum(int min, int max) {
        int randNum = min + (int) (Math.random() * ((max - min) + 1));
        return randNum;
    }

    public static String getRandomVerifyCode() {
        return getRandNum(100000, 999999) + "";
    }

}
