import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
/**
 * Write a description of class Hangman here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hangman
{
   
    private JFrame frame;
    private JPanel left, right, midRight,bottomRight, topRight;
    private JButton generator, instructions, buttona, buttonb, buttonc, buttond, buttone, buttonf, buttong, buttonh, buttoni, buttonj, buttonk, buttonl, buttonm, buttonn, buttono, buttonp, buttonq, buttonr, buttons, buttont, buttonu, buttonv, buttonw, buttonx, buttony, buttonz;
    private JTextArea input, lines, lettersGuessed;
    private JLabel title, or, labelTopLeft, choose, a, letter, toGuess;
    private ImageIcon image, noose,noose2, noose3,noose4, noose5, noose6,noose7,noose8,noose9,noose10,noose11,nooseend;
    private ArrayList<JButton> alphabet = new ArrayList<JButton>();
    private String inputString;
    private int wrongGuess;
    private String guesses = "          User Guesses: \n          ";
    private int letterCount;
    private String unrevealedText;
    private int dashCount;
    private String EnglishLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<Integer> indexArray;
    private List<String> words;
   
    /**
     * Constructor for objects of class Hangman
     */
    public Hangman()
    {
        words = readDictionary();
        makeFrame();
        
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void makeFrame()
    {
        //Creates outer frame of window
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        frame = new JFrame();
        left = new JPanel();
        right = new JPanel();
        topRight = new JPanel();
        midRight = new JPanel();
        bottomRight = new JPanel();
         
        frame.setLayout(new GridLayout(1,2));
        frame.setTitle("The Epic Hangman Game");
        frame.add(left);
        frame.add(right);
        right.setLayout(new GridLayout(3,1));
        
        input = new JTextArea();
        lines = new JTextArea();  
        lines.setFont(new Font("Serif", Font.BOLD, 22));
        lettersGuessed = new JTextArea();
        lettersGuessed.setFont(new Font("Serif", Font.BOLD, 18));
        
        or = new JLabel("                              OR");
        or.setFont(new Font("Serif", Font.BOLD, 18));
        
        instructions = new JButton("Type the phrase to play with in space right, then click here.");
        instructions.setFont(new Font("Serif", Font.BOLD, 12));
        
        generator = new JButton(" GENERATE RANDOM WORD ");
        generator.setFont(new Font("Serif", Font.BOLD, 30));
        
        topRight.setLayout(new GridLayout(1,1));
        midRight.setLayout(new GridLayout(5,6));
        bottomRight.setLayout(new GridLayout(2,0));
        
        bottomRight.add(lines);
        bottomRight.add(lettersGuessed);
        
        
        topRight.add(generator);
        
        right.add(topRight);
        right.add(midRight);
        right.add(bottomRight);
        
        
        labelTopLeft = new JLabel();
        image = new ImageIcon("images/first.png");
        labelTopLeft.setIcon(image);
        left.add(labelTopLeft);
        
        makeAlphabet();
        
        generator.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e) { setUp(); }
            });
        //Needed to size the window and make it visible
        frame.pack();
        frame.setSize(1500,900);
        frame.setVisible(true);
    }
    
        protected List<String> readDictionary() {
        String word;
        List<String> w = new ArrayList<String>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("words.txt"));

            while ((word = bufferedReader.readLine()) != null) {
                w.add(word);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage()); //prints IO exception is file is not found
        }

        return w;
    }
    
    public void makeAlphabet() {

        for(char currentLetter = 'A'; currentLetter <= 'Z';currentLetter++) {
            JButton button = new JButton((new Character(currentLetter)).toString());
            midRight.add(button); 
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e) { checkLetter(button); }
            });
        }
        
       
        choose = new JLabel(" Choose a letter ");
             a = new JLabel("by clicking on a");
        letter = new JLabel("labelled button ");
        toGuess = new JLabel("to guess.");
        choose.setFont(new Font("Serif", Font.PLAIN, 19));
        a.setFont(new Font("Serif", Font.PLAIN, 19));
        letter.setFont(new Font("Serif", Font.PLAIN,  19));
        toGuess.setFont(new Font("Serif", Font.PLAIN, 19));
        midRight.add(choose);
        midRight.add(a);
        midRight.add(letter);
        midRight.add(toGuess);
      
    }
     public void setUp()
    {
        noose = new ImageIcon("images/noose1.png");
        labelTopLeft.setIcon(noose);
        letterCount=0;
        guesses = "          User Guesses: \n          ";
        wrongGuess = 1;
        newWord();
    }
    public void checkLetter(JButton letter) {
       String i = (letter.getText().toLowerCase());
       int k = (inputString.indexOf(letter.getText().toLowerCase()));
       int j = inputString.indexOf(letter.getText());
        if ((inputString.indexOf(letter.getText()) == -1) && (inputString.indexOf(letter.getText().toLowerCase()) == -1))
        {
            
            addAnswer(letter);
            labelTopLeft.setIcon(new ImageIcon("images/noose"+(new Integer(wrongGuess+1)).toString()+".png"));
            wrongGuess++;
            if (wrongGuess==11)
            {
                lines.setText(stuffSpaces(inputString));
                wrongGuess--;
            }
        }
        else {
            updateDashes(letter);
        }
        
    }
    private void addAnswer(JButton letter) {
        guesses += letter.getText() + " ";
        letterCount++;
        if (letterCount >= 3)
        {
            guesses += "\n          ";
            letterCount = 0;
        }
        
        lettersGuessed.setText(guesses);
    }
    private void newWord() {
        int randomNum = (int)(Math.random() * words.size()); 
        inputString = words.get(randomNum).toUpperCase();
        unrevealedText = new String(new char[inputString.length()]).replace("\0", "_");
        lines.setText(stuffSpaces(unrevealedText));
    }
    
        private void updateDashes(JButton letter) {
        unrevealedText = getUpdatedString(unrevealedText,letter.getText().charAt(0));
        if (stuffSpaces(unrevealedText).equals(stuffSpaces(inputString))) {
            unrevealedText += "\n You Won!";
        }
        lines.setText(stuffSpaces(unrevealedText));
    }
    
    private String stuffSpaces(String input)
    {
        StringBuilder result = new StringBuilder("\n ");
        for (int i=0; i<input.length(); i++)
        {
            result.append(input.charAt(i));
            result.append(" ");
        }
        return result.toString();
    }
    
    private String getUpdatedString(String dashed, char letter)
    {
        StringBuilder result = new StringBuilder(dashed);
        for (int i=0; i<dashed.length(); i++)
        {
            if (inputString.charAt(i)==letter)
                result.setCharAt(i,letter);
        }
        return result.toString();
    }
    
   
}
