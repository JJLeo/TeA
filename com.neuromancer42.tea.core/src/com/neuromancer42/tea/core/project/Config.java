package com.neuromancer42.tea.core.project;

import com.neuromancer42.tea.core.util.Utils;

/**
 * System properties recognized by Chord.
 *
 * @author Mayur Naik (mhn@cs.stanford.edu)
 */
public class Config {
    private static final String BAD_OPTION = "ERROR: Unknown value '%s' for system property '%s'; expected: %s";
    public long startTime;

    private static Config v;

    private Config()
	{
        Utils.mkdirs(outDirName);
        Utils.mkdirs(bddbddbWorkDirName);
        Utils.mkdirs(souffleWorkDirName);
        startTime = System.currentTimeMillis();
    }

	public static void init()
	{
        if (v != null) {
            System.err.println("Config: Config has been initialized before, are you sure to re-init?");
        }
		v = new Config();
	}

	public static Config v()
	{
		return v;
	}

    // basic properties about program being analyzed (its main class, classpath, command line args, etc.)

    public final String workDirName = System.getProperty("chord.work.dir", "test-out");
    public final String mainClassName = System.getProperty("chord.main.class");

    public final String runAnalyses = System.getProperty("chord.run.analyses", "");
    public final String printRels = System.getProperty("chord.print.rels", "");
    public final boolean printProject = Utils.buildBoolProperty("chord.print.project", false);
    public final boolean printResults = Utils.buildBoolProperty("chord.print.results", true);
    public final boolean saveDomMaps = Utils.buildBoolProperty("chord.save.maps", true);

    // Determines verbosity level of Chord:
    // 0 => silent
    // 1 => print task/process enter/leave/time messages and sizes of computed doms/rels
    //      bddbddb: print sizes of relations output by solver
    // 2 => all other messages in Chord
    //      bddbddb: print bdd node resizing messages, gc messages, and solver stats (e.g. how long each iteration took)
    // 3 => bddbddb: noisy=yes for solver
    // 4 => bddbddb: tracesolve=yes for solver
    // 5 => bddbddb: fulltravesolve=yes for solver
    public final int verbose = Integer.getInteger("chord.verbose", 1);

    // Chord timeout settings (2 minutes by default)
    public final Integer timeoutMillis = Integer.getInteger("chord.timeout.millis", 120000);

    // Chord project properties
    public final String javaAnalysisPathName = System.getProperty("chord.java.analysis.path");
    public final String dlogAnalysisPathName = System.getProperty("chord.dlog.analysis.path");

    // properties concerning BDDs
    public final boolean useBuddy =Utils.buildBoolProperty("chord.use.buddy", false);
    public final String bddbddbMaxHeap = System.getProperty("chord.bddbddb.max.heap", "1024m");

    // properties specifying names of Chord's output files and directories

    public String outDirName = System.getProperty("chord.out.dir", workRel2Abs("chord_output"));
    public String bddbddbWorkDirName = System.getProperty("chord.bddbddb.work.dir", outRel2Abs("bddbddb"));
    public String souffleWorkDirName = System.getProperty("chord.souffle.work.dir", outRel2Abs("souffle"));
    public final boolean reuseRels =Utils.buildBoolProperty("chord.reuse.rels", false);

    // commonly-used constants

    public final String mainDirName = System.getProperty("chord.main.dir");


    private String outRel2Abs(String fileName) {
        return (fileName == null) ? null : Utils.getAbsolutePath(outDirName, fileName);
    }

    private String workRel2Abs(String fileName) {
        return (fileName == null) ? null : Utils.getAbsolutePath(workDirName, fileName);
    }

    public void check(String val, String[] legalVals, String key) {
        for (String s : legalVals) {
            if (val.equals(s))
                return;
        }
        String legalValsStr = "[ ";
        for (String s : legalVals)
            legalValsStr += s + " ";
        legalValsStr += "]";
        Messages.fatal(BAD_OPTION, val, key, legalValsStr);
    }
}
