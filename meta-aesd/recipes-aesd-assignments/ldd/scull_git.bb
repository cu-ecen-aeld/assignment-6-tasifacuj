inherit update-rc.d
# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-tasifacuj.git;protocol=ssh;branch=master \
            file://S98lddmodules"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "30215904ede51cf3395aa673d5679e23c6fd25d7"

S = "${WORKDIR}/git/scull"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98lddmodules"
INITSCRIPT_PARAMS = "defaults 98"

inherit module

EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

FILES:${PN} += "${bindir}/scull_load ${bindir}/scull_unload ${sysconfdir}/init.d/S98lddmodules"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/scull_load ${D}${bindir}/scull_load
    install -m 0755 ${S}/scull_unload ${D}${bindir}/scull_unload

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/S98lddmodules ${D}${sysconfdir}/init.d/S98lddmodules

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko
}
