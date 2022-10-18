// SPDX-License-Identifier: MIT

module.exports = {
    multipass: true,
    plugins: [
        'removeDoctype',
        'removeXMLProcInst',
        //'removeComments', -> keep copyright notice
        'removeMetadata',
        'removeEditorsNSData',
        'cleanupAttrs',
        'mergeStyles',
        'inlineStyles',
        'minifyStyles',
        'cleanupIDs',
        'removeUselessDefs',
        'cleanupNumericValues',
        'convertColors',
        {
            name: 'removeUnknownsAndDefaults',
            params: {
                keepDataAttrs: false
            }
        },
        'removeNonInheritableGroupAttrs',
        'removeUselessStrokeAndFill',
        //'removeViewBox',
        'cleanupEnableBackground',
        'removeHiddenElems',
        'removeEmptyText',
        'convertShapeToPath',
        'convertEllipseToCircle',
        'moveElemsAttrsToGroup',
        'moveGroupAttrsToElems',
        'collapseGroups',
        'convertPathData',
        'convertTransform',
        'removeEmptyAttrs',
        'removeEmptyContainers',
        'mergePaths',
        'removeUnusedNS',
        'sortDefsChildren',
        'removeTitle',
        'removeDesc'
    ]
}
