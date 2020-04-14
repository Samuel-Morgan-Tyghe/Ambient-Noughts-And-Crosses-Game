# Ambient-TicTacToe-Game
Visual and Auditory twist on the game Noughts And Crosses.
# What I Learnt
* GUI basics,File, AudioInputStream ( clip , play), .getSource(), Mouse/Button/KeyListener (implementation), Paint (gradients)

* Class Hierachy: AudioInputStream isa InputStream , JButton isa AbstractButton isa Object


* place images and sounds in a resource folder in eclipse and use get.rescource, this enables you to package media for code distrubition 


* AudioSystem.getClip,   clip.open, clip.start, throw try and catch ( needs try and catch as exterior errors can occur, ie sound card issues)

* Good Combo: For loops ( new button(Integer.toString(i)),add(buttons[i]), addActionListener, setIcon, setVisible, setRolloverIcon,setPressedIcon) 

* Using methods Correctly

* Iterate twice for 3 in a row pattern checker to save a few lines of code.

xyz| + 3    | +3
-----|---------|-------
**123**  |  123  |  123 
456  |  **456** |  456  
789  |  789   | **789**

xyz| + 1    | +1
-----|---------|-------
**1**23  |  1**2**3  |  12**3** 
**4**56  | 4**5**6 |  45**6** 
**7**89  |  7**8**9   | 78**9**


# What I learnt Afterwards
* Dispose() and restart would be cleaner, and I could use a TXT file to record Highscore

* Spread code to multiple classes

* Windows Builder eclipse extension ( more effecient in the short term ).



