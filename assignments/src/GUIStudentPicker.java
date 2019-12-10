import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class GUIStudentPicker implements ActionListener {
        String[][] randStudents = new String[][] {  {"Bobby", "Earl", "Janet", "Bilbo", "Ellen"},
                {"X", "X", "X", "X", "X"}};
        JFrame frame = new JFrame("Student Picker");
        JLabel nameLabel = new JLabel("Click the button", SwingConstants.CENTER);
        JButton selectButton = new JButton("Pick Student");

        public static void main(String[] args){

            new GUIStudentPicker();

        }

        public GUIStudentPicker(){
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout( 2, 1));
            frame.add(nameLabel);
            frame.add(selectButton);
            selectButton.addActionListener(this);
            nameLabel.setFont(new Font("", Font.BOLD, 40));
            selectButton.setFont(new Font("", Font.BOLD, 40));
            frame.setVisible(true);
        }

        public String randomStudentPicker(){
            int randStudent;
            boolean reset = true;
            boolean repeat = true;
            for (int x = 0; x < randStudents[0].length; x++){ //This checks if all names have been picked
                if (!(randStudents[0][x].equals("X"))){
                    reset = false;
                    break;
                }
            }
            if (reset){ //this resets everything
                for (int x = 0; x < randStudents[0].length; x++){
                    randStudents[0][x] = randStudents[1][x];
                    randStudents[1][x] = "X";
                }
            }
            do { // this chooses the student, making sure its new
                randStudent = (int) (Math.random() * 5);
                if(randStudents[0][randStudent].equals("X")){
                    repeat = true;
                }
                else{
                    break;
                }
            }
            while (repeat);
            randStudents[1][randStudent] = randStudents[0][randStudent]; //this makes sure the student is not included in the next round
            randStudents[0][randStudent] = "X";
            return randStudents[1][randStudent]; //since the student has now been shifted, we choose the lower array
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            nameLabel.setText(randomStudentPicker());
        }
    }
