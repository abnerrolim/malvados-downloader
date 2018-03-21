package org.abner.random;

import org.apache.commons.cli.*;

/**
 * Hello world!
 *
 */
public class App 
{

    private static Options options = new Options();

    static {
        options.addOption( Option.builder()
                .required()
                .longOpt("path")
                .desc("Path directory to save malvado's images")
                .hasArg()
                .argName("PATH")
                .numberOfArgs(1)
                .build());
    }

    public static void main( String[] args )
    {
        CommandLineParser parser = new DefaultParser();
        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption( "path" )){
                MalvadosDownloader downloader = new MalvadosDownloader(cmd.getOptionValue("path"));
                downloader.download();
            }else{
                printHelp();
            }
        }catch (ParseException e){
            System.out.print("Parsing of options failed. Reasong: "  + e.getMessage());
            printHelp();
        }

    }

    private static void printHelp(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("run", options);
    }

}
