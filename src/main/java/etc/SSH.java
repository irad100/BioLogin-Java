package etc;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SSH {

    public void execute(String command)
            throws IOException {
        final SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();

        ssh.connect("localhost");
        try {
            ssh.authPublickey(System.getProperty("user.name"));
            final Session session = ssh.startSession();
            try {
                final Command cmd = session.exec(command);
                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
                cmd.join(5, TimeUnit.SECONDS);
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }
    }

}
