package learn. spring.upload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author FengQing
 */
@Controller
public class FileUpoadController {

    @RequestMapping(value = "/spring/upload/form", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam(value = "name",
            required = false) String name, @RequestParam(value = "file",
            required = false) MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                // store the bytes somewhere
                File f = new File("W\\:a.jpg");
                // FileWriter fw = new FileWriter(f);
                DataOutputStream dos = new DataOutputStream(
                    new FileOutputStream(f));
                dos.write(bytes);
                dos.close();
                return "redirect:uploadSuccess";
            } else {
                return "redirect:uploadFailure";
            }
        } catch (Exception e) {
            return "redirect:uploadFailure";
        }
    }

}