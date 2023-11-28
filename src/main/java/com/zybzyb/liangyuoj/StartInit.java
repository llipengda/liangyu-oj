package com.zybzyb.liangyuoj;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartInit {

    private static final String POLICY_DIR = "./policy/";

    private static final String TEST_DIR = "./tests/";

    private static final String READ_ONLY_PILICY = POLICY_DIR + "read-only.policy";

    @PostConstruct
    public void init() throws IOException {
        log.info("Init test files");
        File testDir = new File(TEST_DIR);
        if (!testDir.exists()) {
            if (testDir.mkdirs()) {
                log.info("Create test directory success");
            } else {
                log.error("Create test directory failed");
            }
        }
        File policyDir = new File(POLICY_DIR);
        log.info("Init policy files");
        if (!policyDir.exists()) {
            if (policyDir.mkdirs()) {
                log.info("Create policy directory success");
            } else {
                log.error("Create policy directory failed");
            }
        }
        String readOnly = """
            grant {
                permission java.io.FilePermission "%s", "read";
                permission java.io.FilePermission "%s", "write";
            };
            """.formatted(testDir.getAbsolutePath(), testDir.getAbsolutePath());
        Files.write(Path.of(READ_ONLY_PILICY), readOnly.getBytes());
    }
}
