# CTL Model Checker 

CTL model checker written in Java and using a JavaCC parser for Texas State CS 5392 Formal Methods Fall '21, taught by Dr. Podorozhny.

## Items Left 
- [x] get user models/formulas working<br>
- [x] descriptive error if state to check isn't in model<br>
- [ ] code up the microwave example (mchPeled.zip) (lanlan)<br>
- [ ] description of acceptance testcases (lanlan)<br>
- [ ] description of execution of acceptancve testcases illustrated with screenshots of all windows/popups of system and console output along testcase (divitha)<br>
- [ ] UML class diagram for the software system (shravanthi)<br>
- [ ] everyone: double check / study Model 4 for presentation<br>
- [ ] powerpoint (divitha)

## How To Run 
1) Install Java 14 (if you have a higher version it probably works, but I haven't tried)
    - Go to https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html
    - You will need to login with an oracle account and will need to create one if you don't have one already
    - I downloaded the macOS Installer after logging in
    - Double click the downloaded file and follow the prompts to install Java 14
2) Install Maven (I think you only need this to run the unit tests)
    - download the binary tar.gz archive from https://maven.apache.org/download.cgi
    - in finder double click the downloaded file to unzip it
    - in terminal cd to the folder the unzipped file
    - `chown -R root:wheel ./apache-maven*`
    - `sudo mv ./apache-maven* /opt/apache-maven` (you will have to enter your password for the sudo to work)
    - Add mvn to your path. In Big Sur, this is done by:
    - `cd ~`
    - do `ls -la` and look for `.zshrc`
    - if `.zshrc` isn't there, do `touch .zshrc`
    - do `nano .zshrc`
    - in nano add this line `export PATH=$PATH:/opt/apache-maven/bin`
    - press control + x to exit and y and then enter to save
    - quit (not close, but quit) terminal
    - reopen terminal
    - type `mvn -version` and if everything worked the response should start with something like `Apache Maven 3.8.3`
3) Clone the repo  
   - `git clone cs5392-group-project-kusupati-liu-mcdermott-namala`
4) Cd into project
5) `cd cs5392-group-project-kusupati-liu-mcdermott-namala`
6) Run the program
    - `java -jar modelCheckingCTL -k <kripke file> [-s <state to check>] -af <formula> -e`
    - The arguments in `[ ]` are optional
    - The model flag takes either `-a` for specifying the formula in directly in the argument (inside quotes) or `-f` for specifying a file which contains the formula. `-e` is to specify to run the end to end tests 
    - The `-s` argument for state to check is optional. If omitted, all states are checked.
    - An alternative way to run the program is to only run the end to end tests, which is specified by - `java -jar modelCheckingCTL -e`
    - Some command line examples:
        - `java -jar modelCheckingCTL -k kripke.txt -s s0 -a "EXp" -e`
        - `java -jar modelCheckingCTL -k kripke.txt -s s13 -f model.txt` 
        - `java -jar modelCheckingCTL -e`
7) Run the unit tests
   - `mvn test`
    
## Development Notes
- To get this running in IntelliJ:
    - Open IntelliJ
    - Open the top level program folder `cs5392-group-project-kusupati-liu-mcdermott-namala`
    - Set the project java SDK to java 14:
    - file -> project structure -> project sdk on the right -> set to java 14.0.2 if not already set to that.
    - Click the green hammer icon at the top to do the initial build
    - Open src/main/java/dev.markmcd/Main
    - Right click the word `Main` on line 15 and select "Run Main.main()"
    - This throws a null pointer exception, which is fine right now
    - Click "Main" at the top of the screen to the right of the green hammer
    - Click Edit Configurations
    - On the right side under "Build and run" in the Program Arguments field, enter `-k kripke.txt -a "EXp" -e` and click Apply and Ok.
    - Click the green play icon to the right of the green hammer and the "Main" configuration.
- JavaCC is used as a compiler/parser here in two instances - once for the validator and once for the parser
    - Validator
        - The validator just checks for well formed CTL models. It is like a much simplified version of the parser.
        - The validator is located at src/main/java/dev/markmcd/controller/ctl/Validator
    - Parser 
        - The parser does the heavy lifting for the CTL work. This is where the SAT algorithms are run and evaluated.
        - The parser is located at src/main/java/dev/markmcd/controller/ctl/Parser
    - Both the validator and the parser use generated files, so the development workflow with them is a little strange.
        - You'll need to <a href="https://javacc.github.io/javacc/#download">download and install JavaCC</a>. I'm on mac (11.4 Big Sur) and I believe these were my install steps:
            - I downloaded the source zipfile at <a href="https://github.com/javacc/javacc/archive/javacc-7.0.10.zip">https://github.com/javacc/javacc/archive/javacc-7.0.10.zip</a>
            - I unzipped the source zipfile in my home directory (`/Users/markmcdermott`)
            - I opened my etc/paths file with `sudo vi etc/paths` 
            - I added this line to the end of my paths file: `/Users/markmcdermott/javacc-javacc-7.0.10/scripts`
            - Then I quit terminal and reopened it 
        - Once JavaCC is installed, you can modify and then and regenerating the grammar file. These steps will modify the parser files, but the same steps will work for the validator as well:
            - `cd` into the parser directory (src/main/java/dev/markmcd/controller/ctl/Parser)
            - Open Parser.jj in your IDE or text editor.
            - All you modifications will happen only in this .jj file.
            - After you're done your changes, in terminal in the parser directory (the one containing Parser.jj), type `javacc Parser.jj`. This will generate about eight files in this same directory.
            - Since every time you regenerate your compiler files they overwrite the previously generated files, you can't make modifications in the generated files or they'll be overwritten the next time you regenerate them.  
            
             
            