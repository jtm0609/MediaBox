import org.gradle.api.artifacts.VersionCatalog

fun VersionCatalog.getLibrary(library: String) = findLibrary(library).get()
fun VersionCatalog.getVersion(version: String) = findVersion(version).get()

fun VersionCatalog.getBundle(bundle: String) = findBundle(bundle).get()
