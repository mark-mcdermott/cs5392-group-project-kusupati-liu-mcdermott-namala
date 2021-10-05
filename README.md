# CTL Model Checker 

CTL model checker written in Java and using a JavaCC parser.

## How To Run 
 1) Prerequisites to run this:
    - Java 14
    - Maven
     
 2) Clone the repo  
    - `git clone cs5392-group-project`
 3) Cd into project
    - `cd cs5392-group-project`
 4) Run the program
    - `java -jar modelCheckingCTL -k <kripke file> [-s <state to check>] -af <model>`
    - The arguments in `[ ]` are optional
    - The model flag takes either `-a` for specifying the model in directly in the argument (inside quotes) or `-f` for specifying a file which contains the model. 
    - The `-s` argument for state to check is optional. If omitted, all states are checked.
    - Some command line examples:
        - `java -jar modelCheckingCTL -k kripke.txt s0 -a "EXp"`
        - `java -jar modelCheckingCTL -k kripke.txt s13 -f model.txt` 
 5) Run the unit tests
    - `mvn test`
    
## Development Notes
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
            
             
            