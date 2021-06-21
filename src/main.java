import com.jcraft.jsch.*;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class main {


        public static File getLatestFilefromDir (String dirPath){
            File dir = new File(dirPath);
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return null;
            }

            File lastModifiedFile = files[0];
            for (int i = 1; i < files.length; i++) {
                if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                    lastModifiedFile = files[i];
                }
            }
            return lastModifiedFile;
        }



        public static void main(String[] argv) throws Exception {

            String copyTo = "D:\\Java\\Copy";
            String remoteDirectoryCopyFrom = "C:\\Users\\Kenya Aliens IT\\Desktop\\sp";


            File lastOne= getLatestFilefromDir(remoteDirectoryCopyFrom);

            String file = lastOne.toString();

            String finalDirectory=remoteDirectoryCopyFrom+"\\"+file;
            //System.out.println(finalDirectory);

//            File dir = new File("C:\\Users\\Kenya Aliens IT\\Desktop\\sp");
//            String[] children = dir.list();
//            if (children == null) {
//                System.out.println("does not exist or is not a directory");
//            } else {
//                for (int i = 0; i < children.length; i++) {
//                    String filename = children[i];
//                    System.out.println(filename);
//                }


                String hostname = "10.42.0.152";
                String username = "";
                String password = "";
                //String copyFrom = "*server path for example /home/file/abc.png *";

                JSch jsch = new JSch();
                Session session = null;
                System.out.println("Trying to connect.....");
                try {
                    session = jsch.getSession(username, hostname, 21);
                    session.setConfig("StrictHostKeyChecking", "no");
                    session.setPassword(password);
                    session.connect();
                    Channel channel = session.openChannel("sftp");
                    channel.connect();
                    ChannelSftp sftpChannel = (ChannelSftp) channel;
                    sftpChannel.get(finalDirectory, copyTo);
                    sftpChannel.exit();
                    session.disconnect();

                } catch (JSchException e) {
                    e.printStackTrace();
                } catch (SftpException e) {
                    e.printStackTrace();
                }
                System.out.println("Done !!");
            }
        }


