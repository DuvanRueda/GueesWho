package co.edu.uptc.data;

import co.edu.uptc.doubleList.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileManager {

    private final String filePath;
    private DoubleList<String> datas;

    public FileManager(String path){
        this.filePath = path;
        datas = new DoubleList<>();
    }

    public DoubleList<String> readFile(){
        File file = new File(filePath);

        if (!file.exists()) createExternalFile();

        try(FileInputStream input = new FileInputStream(file)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;

            while ((line = br.readLine()) != null){
                if (line.startsWith("#") || line.isBlank()) {
                    continue;
                }
                datas.addLast(line);
            }
        } catch (Exception e) {
            writeFile();
            e.printStackTrace();
        }
        return datas;
    }

    private void createExternalFile() {
        try (InputStream input = FileManager.class.getClassLoader().getResourceAsStream(filePath)){

            Path out = Path.of(filePath);
            Path outDirectory = out.getParent();

            if (Files.notExists(outDirectory)) Files.createDirectories(outDirectory);

            Files.copy(input, out, StandardCopyOption.REPLACE_EXISTING);

        }catch (Exception e){

        }
    }

    private void writeFile(){

    }
}
