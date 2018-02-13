package com.titstory.heowc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JunitResourPathTest {

    //2. JUnit 테스트 예제에서 파일 및 리소스 읽기
    //2.1. ClassLoader의 리소스 사용하기
    @Test
    public void testReadFileWithClassLoader(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("lorem_ipsum.txt").getFile());
        assertTrue(file.exists());
    }

    //src/test/resources 디렉토리의 하위 디렉토리에서 파일이나 자원을 읽어야하는 경우 해당 하위 디렉토리에 대한 경로를 지정해야합니다.
    //예를 들어 src/test/resources/data01 디렉토리 에서 users.csv 파일을 읽습니다 .
    @Test
    public void testReadFileWithClassLoader2() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("data01/users.csv").getFile());
        assertTrue(file.exists());

    }

    //2.2. 클래스의 리소스 사용
    //src/test/resources 아래의 파일은 대개 target/test-classes에 복사됩니다.
    //JUnit에서 이러한 리소스 파일에 액세스하려면 클래스의 리소스를 사용할 수 있습니다. 테스트의 classpath/target/test-classes에서 파일을 찾습니다.
    @Test
    public void testReadFileWithResource() {
        URL url = this.getClass().getResource("/lorem_ipsum.txt");
        File file = new File(url.getFile());
        assertTrue(file.exists());
    }

    //"/"는 파일 또는 자원이 src/test/resources 디렉토리에 있음을 의미합니다.
    //파일이나 리소스가 하위 디렉토리에 있다면 src/test/resources에서 해당 하위 디렉토리까지의 상대 경로를 지정해야합니다.
    //users.csv 파일을 src/test/resources/data 디렉토리에서 읽는 예제를 보겠습니다.
    @Test
    public void testReadFileWithResource2() throws IOException{
        InputStream is = this.getClass().getResourceAsStream("/data01/users.csv");
        assertNotNull(is);
    }

    //2.3. 상대 경로 사용
    //src/test/resources 폴더의 상대 경로를 사용하여 JUnit 테스트에서 파일과 리소스를 읽을 수 있습니다. 예를 보자.
    @Test
    public void readFileRelativePath() {
        File file = new File("src/test/resources/lorem_ipsum.txt");
        assertTrue(file.exists());
    }

    //2.4. 스트림으로 JUnit 테스트의 파일 및 리소스 읽기
    //JUnit 테스트의 파일과 리소스 를 스트림으로 직접 읽으려는 경우 클래스의 리소스를 다시 사용할 수 있습니다.
    @Test
    public void testReadAsStream() throws IOException{
        InputStream is = this.getClass().getResourceAsStream("/lorem_ipsum.txt");
        assertNotNull(is);
    }

    //파일이나 리소스가 하위 디렉토리에 있다면 src/test/resources에서 해당 하위 디렉토리까지의 상대 경로를 지정해야합니다.
    //src/test/resources/data01 의 users.csv 파일을 읽는 다음 예제를 보겠습니다 .
    @Test
    public void testReadAsStream2() throws IOException{
        InputStream is = this.getClass().getResourceAsStream("/data01/users.csv");
        assertNotNull(is);
    }
}
