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

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-tasifacuj.git;protocol=ssh;branch=master \
            file://S98aesd_char_driver"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "6c3ed7b71acd7493c02b254dc27a908d5baf0f7c"

S = "${WORKDIR}/git/aesd-char-driver"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98aesd_char_driver"
INITSCRIPT_PARAMS = "defaults 98"

inherit module

EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

FILES:${PN} += "${bindir}/aesdchar_load ${bindir}/aesdchar_unload ${sysconfdir}/init.d/S98aesd_char_driver"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdchar_load ${D}${bindir}/aesdchar_load
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/aesdchar_unload

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/S98aesd_char_driver ${D}${sysconfdir}/init.d/S98aesd_char_driver

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/aesdchar.ko
}
