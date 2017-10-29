apply "de.qaware.qav.analysis.plugins.ShortcutQavPlugin"

analysis("Step 1: Analyze Package Architecture") {
    def packageGraph = createPackageArchitectureView(inputClassesGraph)

    def packageCycleGraph = findCycles(packageGraph, "Package")

    // output:
    printNodes(packageCycleGraph, "packageCycleNodes.txt")
    writeDot(packageGraph, "packageGraph", architecture("Package"))
    writeFile(dependencyGraph, "dependencyGraph.json")
}

analysis("Step 2: Prepare T-View Architecture") {
    // Read the given Architecture DSL file. The architecture will be available
    // under the name defined in the Architecture DSL file; it can be accessed
    // with: architecture("T-View")
    readArchitecture "architecture.groovy"

    // Use that architecture and apply it on the dependency graph.
    // Do so twice: once for the full graph, and once only on the part of graph
    // which represents only classes from the input scope (leaving out referenced
    // 3rd-party classes).
    architectureTView = createArchitectureView(allClassesGraph, architecture("T-View"))
    if (architectureTView == null) {
        println "architectureTView is null"
    }
    architectureTViewOnInput = createArchitectureView(
            inputClassesGraph, architecture("T-View"), "T-View-on-Input")

}

analysis("Step 3: Analyze and check for violations") {
    // Check all architecture rules: all relations must be covered in the architecture
    // definition, all components must actually be implemented, and all rules in the
    // architecture file are really used.
    checkArchitectureRules(architectureTView, architecture("T-View"))

    // Find cycles on the component level
    architectureTViewCycleGraph = findCycles(architectureTView, "T-View")
}

analysis("Step 4: Export as DOT, GraphML, and JSON") {
    // graphical export as DOT (for GraphViz) and GraphML (for yEd)
    writeDot(architectureTView, "architectureTView", architecture("T-View"))
    writeDot(architectureTViewCycleGraph, "architectureTViewCycleGraph",
            architecture("T-View"))
    writeDot(architectureTViewOnInput, "architectureTViewOnInput",
            architecture("T-View"))

    // this is to import it into qav-server for interactive exploration of the
    // dependency graph
    writeFile(dependencyGraph, "dependencyGraph.json")
}
