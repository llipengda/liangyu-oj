package com.zybzyb.liangyuoj.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPUtil {
    private static final String URL = "https://www.icourse163.org/web/j/courseBean.getLastLearnedMocTermDto.rpc?csrfKey=1a7da314895f4765a6557c1bb809ef4a";

    private static final String COOKIE = """
        'EDUWEBDEVICE=eeac82f58241445bb75eb8e611a234b9; WM_TID=I8hM7wJkPmlARAEBBQOUxQmERPbIFr6L; __yadk_uid=DyKb0d0osnczMbE4fBpRW3pBBREyQBef; MOOC_PRIVACY_INFO_APPROVED=true; hasVolume=true; videoVolume=0.8; NTESSTUDYSI=1a7da314895f4765a6557c1bb809ef4a; STUDY_INFO="yd.4d8dd7363c0c4cb48@163.com|8|1533087282|1701180027389"; STUDY_SESS="G357mtRtPNLlMLGz6YTY9oaD37KcPrr3zj8ymfgL2RKWi8Gbt9weg7g6WIc6s21jYsXkTUNF9aAyFt0OrDSXv5HgQHjOtT+Doe4duJ3OJPUGet/C72kk+lSqx30NVVczNEiXiMCYvKO6ont1ymmF+6Zj04IYM1B0XOqwOPN+BSQLhur2Nm2wEb9HcEikV+3FTI8+lZKyHhiycNQo+g+/oA=="; STUDY_PERSIST="DL6a3LFGadXF06+5TOHW1hUD+psbIeabbIxvuOpODONybXcVUpnnMjyC6lpd28IVKDmcLwTpmWUzUu5KdRkkWWyatUDVV0Dw5u8h6bcCXyb1BD1LXk5YoudWM/e+3DpvxoxJ0a6BqiKPQeSEHLOB1S7eZW5ddZ1etdcn/LKt2pDjk7BqVwXUr7398/BvLLl468L8U+/RJBpfYUqBLWERMkqZI6mSEqZKXKmvXH0kNH/ZgpjCC7Iso4RP9U87vJE8LtaQzUT1ovP2MqtW5+L3Hw+PvH8+tZRDonbf7gEH7JU="; NETEASE_WDA_UID=1533087282#|#1663030952411; WM_NI=cNb1pQiuxFZgmwQsfCyUOMcNoGgvtX6CtplT3h%2BcykxW27RYymcEYfkYCWd54zyAzB1o6nmP7go%2FgCsaWHAKvSYU9V1LPzJRZSXEa2VqaDX%2B%2BPtMsRi%2BKt270fjlERfhb3I%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed1cc5faeaaa28fb354adeb8fb7d84f939b8b87d53393bcbda4f234a2a9bcb4db2af0fea7c3b92aa192aed2f35bababb8a7d96dafa99f94e549bb8b81aee246ad99a997f754f69dbc91ef60f1b8e584fc61bc89fed8d153bbb3ada6dc6ea9878db8e73ff8a6b7a2f94af3b1fa8ff05cf5b9a1d0e649b1bf99a9c441b68d97b6bc5ef394a09bc95a9a8d98a6e43a96afafa5db21e990bcaef25aabf58595b13df4baa68dc85aa6b8aed4cc37e2a3""";

    private static final String FORM_DATA = """
        termId=1470985543""";

    public static void main(String[] args) {
        crawl();
    }

    private static void crawl() {
        System.out.println("正在爬取: " + URL);

        try {
            // 将表单数据转换为 x-www-form-urlencoded 格式
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .header("Cookie", COOKIE)
                    .POST(HttpRequest.BodyPublishers.ofString(FORM_DATA))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 处理响应
            System.out.println("响应内容: " + response.body());

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace(System.err);
        }
    }
}