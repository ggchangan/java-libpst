package example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import com.pff.*;

public class Test {
    public static void main(final String[] args) {
        new Test(args[0]);
    }

    public Test(final String filename) {
        try {
            final PSTFile pstFile = new PSTFile(filename);
            System.out.println(pstFile.getMessageStore().getDisplayName());
            this.processFolder(pstFile.getRootFolder());
        } catch (final Exception err) {
            err.printStackTrace();
        }
    }

    int depth = -1;

    public void processFolder(final PSTFolder folder) throws PSTException, java.io.IOException {
        this.depth++;
        // the root folder doesn't have a display name
        if (this.depth > 0) {
            this.printDepth();
            System.out.println(folder.getDisplayName());
        }

        // go through the folders...
        if (folder.hasSubfolders()) {
            final Vector<PSTFolder> childFolders = folder.getSubFolders();
            for (final PSTFolder childFolder : childFolders) {
                this.processFolder(childFolder);
            }
        }

        // and now the emails for this folder
        if (folder.getContentCount() > 0) {
            this.depth++;
            PSTMessage email = (PSTMessage) folder.getNextChild();
            while (email != null) {

                /*
                this.printDepth();
                System.out.println("Email Transport Message Header:\t" + email.getDescriptorNodeId() + " - " + email.getTransportMessageHeaders());
                this.printDepth();
                System.out.println("Email Content:\t" + email.getDescriptorNodeId() + " - " + email.getBody());
                this.printDepth();
                System.out.println("Email Body HTML: " + email.getDescriptorNodeId() + " - " + email.getBodyHTML());
                this.printDepth();
                System.out.println("Email Body Prefix: " + email.getDescriptorNodeId() + " - " + email.getBodyPrefix());
                this.printDepth();
                System.out.println("Email: " + email.getDescriptorNodeId() + " - " + email.getSubject());
                this.printDepth();
                System.out.println("NumberOfRecipients:\t" + email.getNumberOfRecipients());
                this.printDepth();
                System.out.println("ActionDate:\t" + email.getActionDate());
                this.printDepth();
                System.out.println("DisplayBCC:\t" + email.getDisplayBCC());
                */
                /*
                email.getMessageDeliveryTime();
                email.getActionDate();
                email.getClientSubmitTime();
                email.getTaskDueDate();
                email.getTaskStartDate();
                email.getCreationTime();
                email.getDateItem(1);
                email.getLastModificationTime();
                */
                /*
                this.printDepth();
                System.out.println("DisplayCC:\t" + email.getDisplayCC());
                this.printDepth();
                System.out.println("DisplayTo:\t" + email.getDisplayTo());
                this.printDepth();
                System.out.println("InternetMessageId:\t" + email.getInternetMessageId());
                this.printDepth();
                System.out.println("Creation Time:\t" + email.getCreationTime());
                this.printDepth();
                System.out.println("ReceivedByAddress:\t" + email.getReceivedByAddress());
                this.printDepth();
                System.out.println("InReplyToId:\t" + email.getInReplyToId());
                this.printDepth();
                System.out.println("ReplyRecipientNames:\t" + email.getReplyRecipientNames());
                this.printDepth();
                */
                System.out.println(email.getSenderAddrtype());
                System.out.println(email.getSenderEmailAddress());
                System.out.println(email.getSenderName());
                System.out.println(email.getSenderEntryId());
                System.out.println("*********************************************************");
                for (int i=0; i< email.getNumberOfRecipients(); i++) {
                    this.printDepth();
                    System.out.println("RecipientType:\t" + email.getRecipient(i).getRecipientType());
                    this.printDepth();
                    System.out.println("EmailAddress:\t" + email.getRecipient(i).getEmailAddress());
                    this.printDepth();
                    System.out.println("DiplayName:\t" + email.getRecipient(i).getDisplayName());
                    this.printDepth();
                    System.out.println("Flags:\t" + email.getRecipient(i).getRecipientFlags());
                    this.printDepth();
                    System.out.println("RecipientOrder:\t" + email.getRecipient(i).getRecipientOrder());
                    this.printDepth();
                    System.out.println("SmtpAddress:\t" + email.getRecipient(i).getSmtpAddress());
                }
                this.printDepth();
                System.out.println("NumberOfAttachments:\t" + email.getNumberOfAttachments());
                this.printDepth();
                System.out.println(email.getReturnPath());
                System.out.println(email.getURLCompName());
                System.out.println(email.getEmailAddress());
                System.out.println("NumberOfAttachments:\t" + email.getNumberOfAttachments());
                for (int x=0; x<email.getNumberOfAttachments(); x++) {
                    handleAttachment(email.getAttachment(x));
                }
                email = (PSTMessage) folder.getNextChild();
            }
            this.depth--;
        }
        this.depth--;
    }

    public void handleAttachment(PSTAttachment attach) throws IOException, PSTException {
        System.out.println("Content Dispositon:\t" + attach.getAttachmentContentDisposition());
        System.out.println("Method:\t" + attach.getAttachMethod());
        System.out.println("Num:\t" + attach.getAttachNum());
        System.out.println("AttachSize:\t" + attach.getAttachSize());
        System.out.println("ContentId:\t" + attach.getContentId());
        System.out.println("Creation Time:\t" + attach.getCreationTime());
        System.out.println("EmbeddedPSTMessage:\t" + attach.getEmbeddedPSTMessage());
        System.out.println("Filename:\t" + attach.getFilename());
        System.out.println("Filesize:\t" + attach.getFilesize());
        System.out.println("LongFilename:\t" + attach.getLongFilename());
        System.out.println("LongPathname:\t" + attach.getLongPathname());
        System.out.println("MimeSequence:\t" + attach.getMimeSequence());
        System.out.println("MimeTag:\t" + attach.getMimeTag());
        System.out.println("ModificationTime:\t" + attach.getModificationTime());
        System.out.println("Pathname:\t" + attach.getPathname());
        System.out.println("RenderingPosition:\t" + attach.getRenderingPosition());
        /*
        InputStream attachmentStream = attach.getFileInputStream();
        // both long and short filenames can be used for attachments
        String filename = attach.getLongFilename();
        if (filename.isEmpty()) {
            filename = attach.getFilename();
        }
        FileOutputStream out = new FileOutputStream(filename);
        // 8176 is the block size used internally and should give the best performance
        int bufferSize = 8176;
        byte[] buffer = new byte[bufferSize];
        int count = attachmentStream.read(buffer);
        while (count == bufferSize) {
            out.write(buffer);
            count = attachmentStream.read(buffer);
        }
        byte[] endBuffer = new byte[count];
        System.arraycopy(buffer, 0, endBuffer, 0, count);
        out.write(endBuffer);
        out.close();
        attachmentStream.close();
        */
    }

    public void printDepth() {
        for (int x = 0; x < this.depth - 1; x++) {
            System.out.print(" | ");
        }
        System.out.print(" |- ");
    }
}
