apply "de.qaware.qav.analysis.plugins.ShortcutQavPlugin"

analysis("Step 1: Analyze Package Architecture") {
    def packageGraph = createPackageArchitectureView(inputClassesGraph)

    def packageCycleGraph = findCycles(packageGraph, "Package")

    // output:
    printNodes(packageCycleGraph, "packageCycleNodes.txt")
    writeDot(packageGraph, "packageGraph", architecture("Package"))
}

analysis("Step 2: Check for unwanted imports") {
    // find unwanted imports:
    // Don't use java.util.Date (rather Joda or java.time.**), and get rid of an old library:
    def unwantedGraph = findDependenciesTo(allClassesGraph, "java.util.Date", "org.mean.old.stuff.**")
    printNodes(unwantedGraph, "unwantedImports.txt", false)
}

analysis("Step 3: Analyze and check T-View Architecture") {
    // Read the given Architecture DSL file. The architecture will be available under the name
    // defined in the Architecture DSL file; it can be accessed with: architecture("T-View")
    readArchitecture "architecture.groovy"

    // Use that architecture and apply it on the dependency graph.
    // Do so twice: once for the full graph, and once only on the part of graph which represents
    // only classes from the input scope (leaving out referenced 3rd-party classes).
    architectureTView = createArchitectureView(allClassesGraph, architecture("T-View"))
    architectureTViewOnInput = createArchitectureView(inputClassesGraph, architecture("T-View"), "T-View-on-Input")

    // Check all architecture rules: all relations must be covered in the architecture definition,
    // all components must actually be implemented, and all rules in the architecture file are
    // really used.
    checkArchitectureRules(architectureTView, architecture("T-View"))
}

analysis("Step 4: Export as DOT, GraphML, and JSON") {
    // graphical export as DOT (for GraphViz) and GraphML (for yEd)
    writeDot(architectureTView, "architectureTView", architecture("T-View"))
    writeDot(architectureTViewOnInput, "architectureTViewOnInput", architecture("T-View"))

    // this is to import it into qav-app for interactive exploration of the dependency graph
    writeFile(dependencyGraph, "dependencyGraph.json")
}

analysis("Step 5: Read Gradle architecture") {
	if (new File("build/qav-report/gradleDependencyGraph.json").exists()) {
	def gradleGraph = readFile("build/qav-report/gradleDependencyGraph.json")
	writeDot(gradleGraph, "gradleGraph", new de.qaware.qav.architecture.dsl.model.Architecture())
	}
}

analysis("Step 6: Read Gradle architecture") {
    if (new File("target/qav-report/mavenDependencyGraph.json").exists()) {
	def mavenGraph = readFile("target/qav-report/mavenDependencyGraph.json")
	writeDot(mavenGraph, "mavenGraph", new de.qaware.qav.architecture.dsl.model.Architecture())
	}
}